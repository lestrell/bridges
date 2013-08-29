import jpype

class Stack(object):
    # Wrappers may be different in a queue, tree, heap, etc.
    # They could also facilitate cross-language (e.g. network) communication
    def __init__(self, sandbox):
        # Convention > configuration
        assert "Stack" in sandbox, "Error: 'class Stack' undefined."
        assert "Node" in sandbox, "Error: 'class Node' undefined."
        self.stack = sandbox['Stack']()
    
    @property
    def root(self):
        return self.stack.root
    
    def push(self, item):
        # push: from sandbox: unbound
        self.stack.push(item)

class JNI(object):
    def __init__(self):
        # NOTE: not portable, can only be instantiated once (yet)
        jpype.startJVM("/usr/lib/jvm/default-java/jre/lib/amd64/server/libjvm.so")
        