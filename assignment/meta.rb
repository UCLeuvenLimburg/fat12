require 'MetaData'
require 'Html'
require 'Code'
require 'Cpp'
require 'LaTeX'
require 'Image'
require 'Upload'
require 'Html'
require 'Environment'
require './Input.rb'
require 'pathname'


module Quiz
  extend Html::Generation::Quiz
end


class Context
  include Contracts::TypeChecking
  include Html::Generation

  def hash(val)
    Digest::SHA1.hexdigest(val).to_s
  end

  def binary(solution, placeholder=nil)
    abort "Solution #{solution} must be string" unless String === solution

    if not placeholder
    then placeholder = "#{solution.length} bits"
    end

    %Q{<input class="input-box" type="text" solution-hash="#{hash(solution)}" placeholder="#{placeholder}">}
  end

  def decimal(solution)
    abort "Solution #{solution} must be string" unless String === solution

    %Q{<input class="input-box" type="text" solution-hash="#{hash(solution)}" placeholder="decimal">}
  end

  def padded_decimal(solution, placeholder)
    abort "Solution #{solution} must be string" unless String === solution

    %Q{<input class="input-box" type="text" solution-hash="#{hash(solution)}" placeholder="#{placeholder}">}
  end

  def time(solution)
    h, m, s = solution.split(/:/)

    padded_decimal(h, 'hh') + padded_decimal(m, 'mm') + padded_decimal(s, 'ss')
  end

  def date(solution)
    d, m, y = solution.split(%r{/})

    padded_decimal(d, 'dd') + padded_decimal(m, 'mm') + padded_decimal(y, 'yyyy')
  end

  def hex(solution)
    abort "Solution #{solution} must be string" unless String === solution

    placeholder = "x" * solution.length

    %Q{<span class="hex">0x</span><input class="input-box" type="text" solution-hash="#{hash(solution)}" placeholder="#{placeholder}">}
  end

  def bytes(*solution)
    solution.map do |byte|
      abort "Byte must be hex string" unless /^[0-9A-Z]{2}$/ =~ byte

      %Q{<input class="input-box" style="width: 25px;" type="text" solution-hash="#{hash(byte)}" placeholder="xx">}
    end.join
  end
  
  def text(solution, case_sensitive: true)
    abort "Solution #{solution} must be string" unless String === solution
    
    case_sensitive = if case_sensitive then 'yes' else 'no' end
    
    %Q{<input class="input-box" type="text" solution-hash="#{hash(solution)}" case-sensitive="#{case_sensitive}" placeholder="string">}
  end
  
end


meta_object do
  extend MetaData::Actions
  extend Html::Actions
  extend LaTeX::Actions
  extend Image::Actions
  extend Upload::Mixin

  tex_to_png('disk-overview', group_name: :images)

  html_template('fat12', context: Context.new, group_name: 'html')
  
  inherit_remote_directory('fat12')

  uploadable_globs('*.html', 'fat12.img', '*.css', '*.js', '*.png')
  upload_action

  group_action(:full, [:html, :upload])
end
