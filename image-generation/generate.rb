require 'pathname'

class ImageContext
  def file(path:, size:, date_time:)
    path = Pathname.new path
    puts "# Creating #{path}"
    puts "mkdir -p disk/#{path.dirname}"
    puts "dd if=/dev/urandom of=disk/#{path} count=#{size} bs=1"
    puts "touch -m -a -t #{date_time.strftime('%Y%m%d%H%M.%S')} disk/#{path}"
  end
end

class DescriptionBase
  def base_name
    raise "Not implemented"
  end

  def image_name
    "#{base_name}.img"
  end

  def xml_name
    "#{base_name}.xml"
  end

  def block_size
    raise "Not implemented"
  end

  def block_count
    raise "Not implemented"
  end

  def number_of_fats
    raise "Not implemented"
  end

  def volume_name
    raise "Not implemented"
  end

  def root_dir_size
    raise "Not implemented"
  end

  def sectors_per_cluster
    1
  end

  def generate_files(context)
    raise "Not implemented"
  end
end

def generate_image(description)
  puts '# Creating disk image'
  puts "dd if=/dev/zero of=#{description.image_name} bs=#{description.block_size} count=#{description.block_count}"

  puts '# Formatting disk image'
  puts "mkfs.vfat -F 12 -f #{description.number_of_fats} -n #{description.volume_name} -r #{description.root_dir_size} -s #{description.sectors_per_cluster} #{description.image_name}"

  puts '# Mounting'
  puts "mkdir -p disk"
  puts "mount -o loop #{description.image_name} disk"

  puts '# Adding directories and folders'
  description.generate_files( ImageContext.new )

  puts '# Unmounting'
  puts "umount disk"

  puts "# Generating xml"
  puts "java -jar ../image-reader/image-reader.jar #{description.image_name} > #{description.xml_name}"
end



abort 'Specify data file' unless ARGV.length == 1

load ARGV[0]


generate_image( Description.new )
