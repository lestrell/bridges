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


# The student may need to write this too (from java?) as discussed
if __name__ == '__main__':
    import argparse
    ap = argparse.ArgumentParser()
    ap.add_argument("structure", type=argparse.FileType("r"),
                    help='filename of student structure')
    args = ap.parse_args()
    # Not really secure! Should trust source!
    sandbox = {}
    exec args.structure in sandbox
    
    Driver(
        source.GeoJSON(),
        wrapper.Stack(sandbox),
        visual.Terminal # No ()
        ).push_multiple()