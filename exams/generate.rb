require 'LaTeX'
require 'Java'
require 'Upload'
require 'Template'
require 'pathname'
require 'nokogiri'


class Context
  include Contracts::TypeChecking

  def load_image(image)
    raw_xml_data = Java.run_jar( Pathname.new('../image-reader/image-reader.jar'), image )
    Nokogiri::XML(raw_xml_data)
  end

  def xml_file
    "#{name}.xml"
  end
  
  def disk
    @disk ||= load_image(image)
  end
  
  def lookup(xpath)
    node = disk.at_xpath(xpath)

    abort "Could not find #{xpath}" unless node

    node.content
  end
    
  def image
    "#{name}.img"
  end

  def template
    "#{name}.template"
  end

  def dir_path
    "./#{dir}"
  end

  def file_path
    "./#{dir}/#{file}"
  end

  def bytes_per_sector
    lookup('/disk-image/boot-sector/bytes-per-sector').to_i
  end

  def sector_count
    lookup('/disk-image/boot-sector/small-sector-count').to_i
  end

  def disk_size
    bytes_per_sector * sector_count
  end

  def sectors_per_fat
    lookup('/disk-image/boot-sector/sectors-per-fat').to_i
  end

  def bytes_per_fat
    lookup('/disk-image/boot-sector/bytes-per-fat').to_i
  end

  def fat_count
    lookup('/disk-image/boot-sector/fat-count').to_i
  end

  def root_entry_count
    lookup('/disk-image/boot-sector/root-entry-count').to_i
  end

  def fat_offset
    lookup('/disk-image/boot-sector/fat-offset').to_i
  end

  def root_offset
    lookup('/disk-image/boot-sector/root-directory-offset').to_i
  end

  def data_offset
    lookup('/disk-image/boot-sector/data-offset').to_i
  end

  def sectors_per_cluster
    lookup('/disk-image/boot-sector/sectors-per-cluster').to_i
  end

  def bytes_per_cluster
    lookup('/disk-image/boot-sector/cluster-size-in-bytes').to_i
  end

  def dir_bytes
    lookup(%{/disk-image/directory[./path/text()='#{dir_path}']/raw-bytes}).split(/ /).map do |x|
      %{"#{x}"}
    end.join(',')
  end

  def file_bytes
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/raw-bytes}).split(/ /).map do |x|
      %{"#{x}"}
    end.join(',')
  end

  def file_time_bits
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/last-write-time-bits}).split(/ /).join(',')
  end
  
  def file_date_bits
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/last-write-date-bits}).split(/ /).join(',')
  end

  def file_time
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/last-write-time})
  end

  def file_date
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/last-write-date})
  end

  def file_clusters
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/clusters}).split(/ /).join(', ')
  end

  def last_bytes
    lookup(%{/disk-image/file[./path/text()='#{file_path}']/contents}).split(/ /)[-4..-1].join(' ')
  end
end


def main
  Dir['*.img'].each do |img|
    puts "Processing #{img}"

    image_path = Pathname.new img
    template_path = Pathname.new "img.template"
    tex_filename = image_path.sub_ext('.tex').to_s

    data = Template.generate(template_path.read, Context.new)

    File.open(tex_filename, 'w') do |out|
      out.write data
    end
  end
end

main
