require 'MetaData'
require 'Upload'


meta_object do
  extend MetaData::Actions
  extend Upload::Mixin

  def remote_directory
    Pathname.new '/var/www/courses/bs1'
  end
end
