#import jpype
import imp

class Stack(object): 
    # Wrappers may be different in a queue, tree, heap, etc.
    def __init__(self, wrapper):
        self.stack = wrapper.Stack()
    
    @property
    def root(self):
        return self.stack.root
        
    def push(self, element):
        self.stack.push(element)

def Sandbox(filename):
    # Very short since it's basically just python on python
    return imp.load_source('student', filename)

def JNI(filename):
    # NOTE: not portable, and can only be run once (yet)
    # see examples here http://iacobelli.cl/blog/?p=119
    classpath = os.path.dirname(filename)
    classname = os.path.splitext(os.path.basename(filename))[0].title()
    jpype.startJVM("/usr/lib/jvm/default-java/jre/lib/amd64/server/libjvm.so",
		   "-ea", "-Djava.class.path=%s" %classpath)
    # -- incomplete -- returns a class rather than a namespace
    return jpype.JClass(classname)
