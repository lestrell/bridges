import org.scalatest._
import bridges._
import org.apache.http.client.fluent
import org.json.simple._
import java.io.IOException

class Empty(val username:String="", val password:String="", val http_connection:fluent.Executor=null) extends DummyConnectable {}

class ConnectableTest extends FlatSpec with Matchers {
    "DummyConnectable" should "respond with a predefined string" in {
        val dummy = new Empty()
        dummy.response = "response"
        dummy.http(fluent.Request.Get("url")) should be("response")
        dummy.get("url") should be("response")
        dummy.post("url", Map()) should be("response")
        dummy.post("url", Map("x" -> "y")) should be("response")
    }
    
    "DummyConnectable" should "correctly handle empty responses" in {
        val dummy = new Empty()
        dummy.response = ""
        dummy.http(fluent.Request.Get("url")) should be("")
        dummy.get("url") should be("")
        dummy.post("url", Map()) should be("")
        dummy.post("url", Map("x" -> "y")) should be("")
    }
    
    "DummyConnectable" should "decode JSON" in {
        val dummy = new Empty()
        dummy.response = """{"x": "y"}"""
        dummy.getjs("url") should not be(null)
        dummy.getjs("url").get("x") should be("y")
    }
    
    "DummyConnectable" should "complain about empty JSON" in {
        val dummy = new Empty()
        dummy.response = ""
        dummy.get("url") should be("")
        a [IOException] should be thrownBy {
            dummy.getjs("url")
        }
    }
}