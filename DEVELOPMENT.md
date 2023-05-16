# Project build & Test

## Problem

### API Endpoint

You can request the data using the following endpoint:

```
http://app-homevision-staging.herokuapp.com/api_project/houses
```

This route by itself will respond with a default list of houses (or a server error!). You can use the following URL parameters:

- `page`: the page number you want to retrieve (default is 1)
- `per_page`: the number of houses per page (default is 10)

### Requirements

- Requests the first 10 pages of results from the API
- Parses the JSON returned by the API
- Downloads the photo for each house and saves it in a file with the name formatted as:

    `[id]-[address].[ext]`

- Downloading photos is slow so please optimize them and make use of concurrency

## Solution

This project was build with spring boot and it contains a series of unit tests meant to test each logic.

The debug information is located on the execution dir with the name `photos-downloader.log`

## Build

Build using maven

```
./mvnw package
```

## Tests


```
java -jar target/photos-downloader-0.0.1-SNAPSHOT.jar -d out -n 1
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to out/1-495MarshRoadPortageIN46368.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to out/6-95908thLaneSeymourIN47274.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to out/7-362LancasterDrOakForestIL60452.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to out/4-7798PoplarStStillwaterMN55082.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to out/3-52SouthRidgeStViennaVA22180.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to out/5-606SilverSpearLaneDefianceOH43512.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to out/0-4PumpkinHillStreetAntiochTN37013.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to out/8-9230WHowardStreetWarminsterPA18974.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to out/9-9EvergreenStYoungstownOH44512.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to out/2-7088NWildRoseAveHartfordCT06106.jpg

```
