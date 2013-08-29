class StackTerminal(object):
    # Visualizations will need to be specific to a structure type
    # They may need to know what the data represents (messages? lat/lon?)
    #   if the visualization formats (globe? timeline?) are specific about that.
    def __init__(self, wrapper):
        self.wrapper = wrapper
        print "Running: %s" %self.wrapper.__class__.__name__
    
    def update(self, item):
        # Update what is displayed on-screen
        
        # Make the stack into a list
        stack = []
        node  = self.wrapper.root
        limit = 25
        while node and limit:
            stack.append(node)
            limit -= 1
            node = node.next
        
        # Format (assuming elements are strings)
        stack = ['# {:<76} #'.format(str(node.element)[:76]) for node in stack]
        stack = '\n'.join(stack)
        stack = '{1:#<80}\n{0}\n{1:#<80}'.format(stack, '')
        print stack