#
# STUDENT WORK
# This would be from another source (across a network, in Java probably.)
#
class Stack(object):
    def __init__(self):
        self.root = None
    
    def push(self, item):
        self.root = Node(item, self.root)
    
    def pop(self):
        if self.root is None:
            return None
        node, self.root = self.root, self.root.nextnode
        return node

class Node(object):
    def __init__(self, element, next):
        self.element, self.next = element, next