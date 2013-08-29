#!/usr/bin/env python

from setuptools import setup, find_packages 

setup(name='bridges',
  author='Bridges Team',
  description='Bridges Prototype',
  entry_points = {
    'console_scripts' : [ 'bridges = bridges.bridges:run' ]
  },
  install_requires = ['requests>=1.2.3', 'rauth', 'JPype1'],
  # JPype1 is a fork but it has what we need and installs easily
  license='GPLv3',
  packages=['bridges'],
  url='-- none -- ',
  version='0.1a0',
 )
