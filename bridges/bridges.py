#!/usr/bin/python
# Bridges API prototype
import source
import wrapper
import visual

class Driver(object):
    def __init__(self, source_inst, wrapper_inst, visual_class):
        self.source, self.wrapper = source_inst, wrapper_inst
        self.visual = visual_class(self.wrapper)
    
    def push_multiple(self):
        for item in self.source.update():
            self.wrapper.push(item)
            self.visual.update(item)

def run():
    import argparse
    sources = {name: value for name, value in source.__dict__.iteritems() if type(value) is type}
    ap = argparse.ArgumentParser()
    ap.add_argument("source", choices=sources.keys(),
                    help='data source')
    ap.add_argument("structure", type=argparse.FileType("r"),
                    help='filename of student structure')
    args = ap.parse_args()
    # Not really secure! Should trust source!
    sandbox = {}
    exec args.structure in sandbox
    
    # This is basically equivalent to what the student would have to write 
    Driver(
        sources[args.source](),
        wrapper.Stack(sandbox),
        visual.Terminal # No ()
        ).push_multiple()

if __name__ == '__main__':
    run()