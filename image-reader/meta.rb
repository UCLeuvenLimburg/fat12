require 'MetaData'
require 'Contracts'
require 'find'
require 'fileutils'
require 'pathname'

meta_object do
  extend MetaData::Actions

  action(:compile, description: 'Compiles source code') do
    paths = []
    
    Find.find('.') do |path|
      path = Pathname.new path

      if path.extname == '.java'
      then paths << path
      end
    end

    FileUtils.mkdir_p 'bin'
    `javac -source 1.8 -sourcepath src -d bin -cp junit-4.11.jar #{paths.map(&:to_s).join(' ')}`
  end

  action(:jar, description: 'Creates image-reader.jar') do
    Dir.chdir 'bin' do
      paths = []

      Find.find('.') do |path|
        path = Pathname.new path

        if path.extname == '.class'
        then paths << path
        end
      end
      
      `jar cfe ../image-reader.jar fat12.App #{paths.map(&:to_s).join(' ')}`
    end
  end

  group_action(:full, [ :compile, :jar ])
end
