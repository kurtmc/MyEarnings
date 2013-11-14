#!/bin/bash
# 1x    -   mdpi    48x48 px
# 1.5x  -   hdpi    72x72 px
# 2x    -   xhdpi   96x96 px
# 3x    -   xxhdpi  144x144 px
# 4x    -   xxxhdpi 192x192 px
# Google play store 512x512 px

# setup constants
filename="icon.svg"
directories=( icons icons/drawable-mdpi icons/drawable-hdpi icons/drawable-xhdpi icons/drawable-xxhdpi icons/drawable-xxxhdpi )

# Make directory if it does not exist
for directory in "${directories[@]}"
do
    if [ ! -e $directory ]
    then
        mkdir $directory
    fi
done

# Skipping the first element of the array generate an icon in each resolution
resolutions=( 48 72 96 144 192 )
for (( i=1; i<=${#resolutions[@]}; i++ ))
do
    dir=${directories[$i]}
    res=${resolutions[$(($i-1))]}
    echo $dir $res
    rsvg-convert $filename -o $dir/ic_launcher.png -w $res -h $res
done
