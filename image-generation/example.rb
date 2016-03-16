# Naam van de image (het bestand)
IMAGE='fat12-19juni-vm.img'


#
# Beschrijving van de schijf
#

# Grootte blocks
BLOCK_SIZE=512

# Aantal blocks
BLOCK_COUNT=1024


#
# FAT12 parameters
#

# Aantal FATs
NUMBER_OF_FATS=2

# Naam
VOLUME_NAME='19A'

# Aantal entries mogelijk in root
ROOT_DIR_SIZE=512

# Aantal sectors per cluster
SECTORS_PER_CLUSTER=1


#
# Inhoud van de schijf
#

# Aantal directories (zullen dir1, dir2, ... heten)
NUMBER_OF_DIRECTORIES=8

# Aantal bestanden per directory (dir1/file1, dir2/file2, ...)
NUMBER_OF_FILES_PER_DIRECTORY=6

# Aantal random bytes per bestand
NUMBER_OF_BYTES_PER_FILE=1787

