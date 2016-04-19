abort 'Specify data file' unless ARGV.length == 1

load ARGV[0]


def pad(x)
  x.to_s.rjust(2, '0')
end


puts 'Creating disk image'
`dd if=/dev/zero of=#{IMAGE} bs=#{BLOCK_SIZE} count=#{BLOCK_COUNT}`


puts 'Formatting disk image'
`mkfs.vfat -F 12 -f #{NUMBER_OF_FATS} -n #{VOLUME_NAME} -r #{ROOT_DIR_SIZE} -s #{SECTORS_PER_CLUSTER} #{IMAGE}`

puts 'Mounting'
`mkdir -p disk`
`mount -o loop #{IMAGE} disk`

puts 'Adding directories and folders'
(1..NUMBER_OF_DIRECTORIES).each do |i|
  `mkdir disk/dir#{i}`

  (1..NUMBER_OF_FILES_PER_DIRECTORY).each do |j|
    `dd if=/dev/urandom of=disk/dir#{i}/file#{j} count=#{NUMBER_OF_BYTES_PER_FILE} bs=1`
    day = rand(27) + 1
    month = rand(12) + 1
    year = rand(35) + 1980
    hour = rand(12)
    mins = rand(60)
    secs = rand(60)

    `touch -m -a -t #{year}#{pad month}#{pad day}#{pad hour}#{pad mins}.#{pad secs} disk/dir#{i}/file#{j}`
  end
end

puts 'Unmounting'
`umount disk`

puts "Generating xml"
`java -jar ../image-reader/image-reader.jar #{IMAGE} > #{File.basename(IMAGE, '.img')}.xml`
