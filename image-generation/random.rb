class Array
  def pick
    self[rand length]
  end
end

class Description < DescriptionBase
  def initialize(*args)
    if args.length == 1
    then @basename = args[0]
    end
  end

  def base_name
    @basename ||= ('image' + rand(1000).to_s)
  end

  def block_size
    @block_size ||= [ 512, 1024, 2048 ].pick
  end

  def block_count
    @block_count ||= (rand 1000 + 500)
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
        size = rand(bs) * rand(5) + 5
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
