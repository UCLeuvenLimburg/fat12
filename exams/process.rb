# Requires X.img and X.tex
# Renames files according to pattern
# Sets tex parameters (date, time, examinator)

require 'fileutils'
require 'pathname'

def remove_solutions
  Dir['*-solution.tex'].each do |file|
    puts "Removing #{file}"
    FileUtils.rm file
  end
end

def produce_solutions
  remove_solutions
  
  Dir['*.tex'].each do |file|
    puts "Producing solution twin for #{file}"

    path = Pathname.new file
    basename = path.basename('.tex').to_s

    File.open("#{basename}-solution.tex", 'w') do |out|
      out.puts '\def\solution{}'
      out.puts(IO.read file)
    end
  end
end


def process(index:, day:, time:, examinator:)
  base = "fat12-#{day}-#{time.gsub(/:/,'')}"
  
  contents = IO.read("disk#{index}.tex")
  contents.gsub!(/datum={TODO}/, "datum={#{day} mei 2016}")
  contents.gsub!(/beginuur={TODO}/, "beginuur={#{time}}")
  contents.gsub!(/examinator={.*?}/, "examinator={#{examinator}}")
  
  File.open("#{base}.tex", 'w') do |out|
    out.write contents
  end

  File.open("#{base}-solution.tex", 'w') do |out|
    out.write '\def\solution{}'
    out.write contents
  end

  FileUtils.cp("disk#{index}.img", "#{base}.img")
end

def process_all
  vdm = 'M.~Vandemaele'
  js = 'J.~Strypsteen'
  
  process(index: 1, day: 15, time: '9:00', examinator: vdm)
end


# remove_solutions
process_all
produce_solutions
