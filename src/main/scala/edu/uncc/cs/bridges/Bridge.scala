package edu.uncc.cs.bridges
import java.util
import java.util.ArrayDeque
import org.json.simple._
import scala.collection._
import scala.collection.JavaConverters._
import scala.collection.mutable.Set
import scala.collection.mutable.ListBuffer

/** Network-enabled sample data aggregator.
  * Bridges offers connectivity for students to more easily use interesting real
  * world data for introductory projects. */
class Bridge(val assignment: Int) extends KeyConnectable {
    
    /** 
     * Connect to a streaming data source such as a social network feed.
     * This feature is not yet complete. Use at your own peril.
     * 
     * @unimplemented
     * @param name  The name of the stream to receive
     * */
    def stream(name: String)= {
        new BStream(this, name)
    }
    
    /**
     * Upload visualization data.
     * Note that only one contiguous group will be shown.
     * So if you only see half of the graph you expected, make sure that there
     * is a path between the center node and the missing nodes.
     *  
     *  @param graph: a Graph or a subclass of Graph
     *  @param center: the central node of the visualization
     *  
     *  @return JSON-encoded String
     */
    def displayGraph(center: String, graph: Graph)= {
    	// Create and upload JSON
        post("/assignments/$assignment", generateGraphJson(center, graph))
        // Return a URL to the user
        s"/assignments/${assignment}/YOUR_USERNAME"
    }
    
    /**
     * Split an identifier into its constituent parts.
     * An identifier should look like "example.com/username".
     * @private
     * @param identifier
     * @return a length-2 String Array of [provider, username]
     */
    def splitIdentifier(identifier: String)= {
    	if (! identifier.contains("/")) {
    		throw new RuntimeException("Provider or screenname missing. Should look like: example.com/username")
    	}
    	identifier.split("/", 2) 
    }
    
    /**
     * Generate the JSON to be uploaded.
     * It uses a BFS, and expects one contiguous group of nodes.
     * 
     * So if you only see half of the graph you expected, make sure that there
     * is a path between the center node and the missing nodes.
     * 
     *  This method is not idempotent; it will edit the graph and will not
     *  produce an accurate graph the second time.
     *  
     *  This method can be useful for debugging. But you should usually prefer
     *  to use displayGraph.
     *  
     *  @param graph: a Graph or a subclass of Graph
     *  @param center: the central node of the visualization
     * 
     */
    def generateGraphJson(center: String, graph: Graph)= {
    	val VISITED=1
    	val open = new ArrayDeque[String]();
    	graph.clearMarks();
    	graph.setMark(center, VISITED);
    	open.add(center);
    	
    	// These maps will be JSON objects
    	var edges = ListBuffer[Map[String, String]]()
    	var nodes = ListBuffer[Map[String, String]]()
    	// Gives the nodes a natural order
    	var names = ListBuffer[String]()
    	  
    	// Traverse the graph, as a BFS (although a DFS would work well too) 
    	while (! open.isEmpty()) {
    	  val local = open.remove()
    	  val name = splitIdentifier(local)(1)
    	  nodes += Map(
    	      "name" -> name,
    	      "color" -> Option(graph.getNodeColor(local)).getOrElse("")
	      )
	      names += name
    	  var neighbor = graph.first(local)
    	  while (neighbor != null) {
    	      edges += Map(
    	          "source" -> name,
    	          "target" -> splitIdentifier(neighbor)(1),
    	          "color" -> Option(graph.getEdgeColor(local, neighbor)).getOrElse(""),
    	          "value" -> graph.getEdge(local, neighbor).toString()
	          )
    	      if (graph.getMark(neighbor) != VISITED) {
    	    	  graph.setMark(neighbor, VISITED)
    	    	  open.add(neighbor);
    	      }
    	      neighbor = graph.next(local)
    	  }
    	}
    	
    	// Convert the JSON-like maps into real JSON
    	var node_string = nodes
			.toList.sortBy( _("name") )  // Improve testability
			.map {
    			_
    				.map({case (k, v) => s""""$k":"$v""""})
    				.reduceOption(_+","+_)
    				.getOrElse("")
    		}
			.map(a => s"{$a}")
			.reduceOption(_+","+_)
			.map(a => s"[$a]")
			.getOrElse("[]")
			
		// Repeat for edges
    	var edge_string = edges
			.map { m =>
				s"""{"source":${names.indexOf(m("source"))},"target":${names.indexOf(m("target"))},"color":"${m("color")}","value":${m("value")}}"""
			}
			.reduceOption(_+","+_)
			.map(a => s"[$a]")
			.getOrElse("[]")
    	s"""{"nodes":$node_string,"links":$edge_string}"""
    }
    
    /** List the user's followers as more FollowGraphNodes.
        Limit the result to `max` followers. Note that results are batched, so
        a large `max` (as high as 200) _may_ only count as one request.
        See Bridges.followgraph() for more about rate limiting. */
    def neighbors(identifier: String, max: Integer=100)= {
    	val Array(service, screen_name) = splitIdentifier(identifier) 
        (try {
	        val response = getjs(s"/streams/$service/followers/$screen_name/$max")
	        val users = response.get("followers").asInstanceOf[util.List[JSONValue]]
	        if (users == null) {
	            // Twitter sometimes gives us garbage
	    		List()
	        } else {
	        	(0 until users.size()).map {
	        		i => s"$service/${users.get(i).asInstanceOf[String]}"
	        	}
	        }
        } catch {
        	case e: RateLimitException => List()
    	}).asJava
    }
    
    def movies(actor: String)= {
        (try {
	        val response = getjsarray(s"/streams/actors/$actor")
	        val movies_json = response.asInstanceOf[util.List[JSONValue]]
        	(0 until movies_json.size()).map {
        		i => movies_json
    				.get(i).asInstanceOf[JSONObject]
    				.get("title").asInstanceOf[String]
        	}
        } catch {
        	case e: RateLimitException => List()
        	// TODO: Decide whether to warn, error, or eat cast exceptions
        	case e: ClassCastException => List()
    	}).asJava
    }
    
    def actors(movie: String)= {
        List[String]().asJava
    }
}