#!/usr/bin/python
# Bridges API prototype
import source
import wrapper
import visual

class Driver(object):
    def __init__(self, source_inst, student, visual_class):
        self.source = source_inst
        self.student = student
        self.visual = visual_class(student)

    
    def push_multiple(self):
        for item in self.source.update():
            self.student.push(item)
            self.visual.update(item)

def run():
    import argparse
    sources = {name: value for name, value in source.__dict__.iteritems() if type(value) is type}
    ap = argparse.ArgumentParser()
    ap.add_argument("source", choices=sources.keys(), help='data source')
    ap.add_argument("structure", type=str, help='filename of student structure')
    args = ap.parse_args()
    
    # The comments describe how this would change if the student wrote this
    Driver(
        # Equivalently: GeoJSON()
        sources[args.source](),  
        
        # Could be:
        #    wrapper.Heap(MyHeap())
        # or if MyHeap implements push() or inherits/mixes in from (our) class:
        #    MyHeap()
        wrapper.Stack(wrapper.Sandbox(args.structure)),
        
        # No change
        visual.StackTerminal
        ).push_multiple()
    
    ''' So the student might write:
    Driver( Twitter(), CircularQueue(), CircularQueueWebVisual() ).push_multiple()
    or the equivalent in another language
    '''

if __name__ == '__main__':
    run()
