require 'MetaData'
require 'LaTeX'
require 'Upload'


meta_object do
  extend MetaData::Actions
  extend LaTeX::Actions
  extend Upload::Mixin

  tex_action('fat12-reference.tex')
  group_action(:tex, [ 'fat12-reference.tex' ])

  inherit_remote_directory 'reference'

  uploadable('fat12-reference.pdf')
  upload_action

  group_action(:full, [:tex, :upload])
end
