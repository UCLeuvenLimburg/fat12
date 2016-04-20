require 'pathname'

class ImageContext
  def initialize(out)
    @out = out
  end

  def file(path:, size:, date_time:)
    path = Pathname.new path
    @out.puts "# Creating #{path}"
    @out.puts "mkdir -p disk/#{path.dirname}"
    @out.puts "dd if=/dev/urandom of=disk/#{path} count=#{size} bs=1"
    @out.puts "touch -m -a -t #{date_time.strftime('%Y%m%d%H%M.%S')} disk/#{path}"
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

class Array
  def pick
    self[rand length]
  end
end

class RandomDescription < DescriptionBase
  def initialize(*args)
    if args.length == 1
    then 
      @basename = args[0]
      srand args[0].hash
    end
  end

  def base_name
    @basename ||= ('image' + rand(1000).to_s)
  end

  def block_size
    @block_size ||= [ 512, 1024, 2048 ].pick
  end

  def block_count
    @block_count ||= (rand 500 + 500)
  end

  def number_of_fats
    @number_of_fats ||= [1, 2].pick
  end

  def volume_name
    @volume_name ||= "VOL#{rand 100}"
  end

  def root_dir_size
    root_dir_size ||= [ 128, 224, 384, 512 ].pick
  end

  def sectors_per_cluster
    1
  end

  def generate_files(context)
    bs = block_size

    context.instance_eval do
      (1..10).each do |i|
        size = bs * (rand(5) + 2) + rand(bs)
        year = 1980 + rand(35)
        month = 1 + rand(12)
        day = 1 + rand(28)
        hour = rand(12)
        mins = rand(60)
        secs = rand(60)

        file(path: "dir#{i}/file", size: size, date_time: Time.new(year, month, day, hour, mins, secs))
      end
    end
  end
end


def generate_image(description, out)
  out.puts '# Creating disk image'
  out.puts "dd if=/dev/zero of=#{description.image_name} bs=#{description.block_size} count=#{description.block_count}"

  out.puts '# Formatting disk image'
  out.puts "mkfs.vfat -F 12 -f #{description.number_of_fats} -n #{description.volume_name} -r #{description.root_dir_size} -s #{description.sectors_per_cluster} #{description.image_name}"

  out.puts '# Mounting'
  out.puts "mkdir -p disk"
  out.puts "mount -o loop #{description.image_name} disk"

  out.puts '# Adding directories and folders'
  description.generate_files( ImageContext.new(out) )

  out.puts '# Unmounting'
  out.puts "umount disk"

  out.puts "# Generating xml"
  out.puts "java -jar ../image-reader/image-reader.jar #{description.image_name} > #{description.xml_name}"
end

(1..100).each do |i|
  basename = "disk#{i}"
  filename = "#{basename}.sh"

  File.open(filename, 'w') do |out|
    begin
      generate_image( RandomDescription.new(basename), out )
    rescue
      puts "Failed on #{filename}"
    end
  end
end
