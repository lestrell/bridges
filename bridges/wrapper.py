#import jpype
import imp

class Stack(object): 
    # Wrappers may be different in a queue, tree, heap, etc.
    def __init__(self, wrapper):
        self.stack = wrapper.Stack()
        self.node = wrapper.Node
    
    @property
    def root(self):
        return self.stack.root
        
    def push(self, element):
        self.stack.push(element)

def Sandbox(filename):
    # Very short since it's basically just python on python
    return imp.load_source('student', filename)

class JNI(object):
    def __init__(self):
        # NOTE: not portable, can only be instantiated once (yet)
        jpype.startJVM("/usr/lib/jvm/default-java/jre/lib/amd64/server/libjvm.so")
        
