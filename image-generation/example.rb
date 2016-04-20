class Description < DescriptionBase
  def base_name
    'example'
  end

  def block_size
    512
  end

  def block_count
    1024
  end

  def number_of_fats
    2
  end

  def volume_name
    'VOLUME'
  end

  def root_dir_size
    512
  end

  def sectors_per_cluster
    1
  end

  def generate_files(context)
    context.instance_eval do
      file(path: 'dir/file', size: 1000, date_time: Time.new(2000,1,1,12,0,0))
    end
  end
end
