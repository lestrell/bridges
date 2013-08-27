class Terminal(object):
    # Visualizations will need to be specific to a structure type
    # They may need to know what the data represents (messages? lat/lon?)
    #   if the visualization formats (globe? timeline?) are specific about that.
    def __init__(self, wrapper):
        self.wrapper = wrapper
        print "Running: %s" %self.wrapper.__class__.__name__
    
    def update(self, item):
        print "Should have added %s" %repr(item)[:50]
        print "Root is now %s" %repr(self.wrapper.root)[:50]