# Houses photo downloader

## How to use

usage: `photos-downloader [-d <arg>] [-help] [-n <arg>] [-s <arg>] [-u <arg>]`

- `-d,--dest <arg>`  
Download directory. Default .
- `-help`  
Show Help
- `-n,--pages <arg>`
  Number of pages to download. Default 10
- `-s,--size <arg>`  
Size of pages to download. Default 10
- `-u,--url <arg>`  
Base URL for the download API. Default <http://app-homevision-staging.herokuapp.com/api_project/houses>

The debug information is located on the execution dir with the name `photos-downloader.log`

## Notes

The output file name has escaped address (removing non-alphanumeric characters):  
`[id]-[address].[ext]`


## Building with Docker

Provided docker configuration to build and run without java

```
$ docker build -t fcoronel/photosdownloader .
$ docker run -v /tmp/:/tmp/ fcoronel/photosdownloader
```
