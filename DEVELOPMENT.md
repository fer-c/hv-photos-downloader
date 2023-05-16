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

### Local


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

### Docker 

#### Bulding Docker Images

```
 docker build -t fcoronel/photosdownloader .
[+] Building 0.8s (13/13) FINISHED
 => [internal] load build definition from Dockerfile................................................................................. 0.0s
 => => transferring dockerfile: 492B................................................................................................. 0.0s
 => [internal] load .dockerignore.................................................................................................... 0.0s
 => => transferring context: 420B.................................................................................................... 0.0s
 => [internal] load metadata for docker.io/library/openjdk:17........................................................................ 0.7s
 => [internal] load build context.................................................................................................... 0.0s
 => => transferring context: 4.38kB.................................................................................................. 0.0s
 => [build 1/7] FROM docker.io/library/openjdk:17@sha256:528707081fdb9562eb819128a9f85ae7fe000e2fbaeaf9f87662e7b3f38cb7d8............ 0.0s
 => CACHED [build 2/7] WORKDIR /workspace/app........................................................................................ 0.0s
 => CACHED [build 3/7] COPY mvnw .................................................................................................... 0.0s
 => CACHED [build 4/7] COPY .mvn .mvn................................................................................................ 0.0s
 => CACHED [build 5/7] COPY pom.xml ................................................................................................. 0.0s
 => CACHED [build 6/7] COPY src src.................................................................................................. 0.0s
 => CACHED [build 7/7] RUN ./mvnw install -DskipTests................................................................................ 0.0s
 => CACHED [stage-1 2/2] COPY --from=build /workspace/app/target/photos-downloader-0.0.1-SNAPSHOT.jar photosdownloader.jar........... 0.0s
 => exporting to image............................................................................................................... 0.0s
 => => exporting layers.............................................................................................................. 0.0s
 => => writing image sha256:9549c798ba8e195af46728b09c7e4800dd791cab984ab343765e38f806d2a2a6......................................... 0.0s
 => => naming to docker.io/fcoronel/photosdownloader................................................................................. 0.0s
```

#### Running
```
 docker run -v /tmp/:/tmp/ fcoronel/photosdownloader
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/4-7798PoplarStStillwaterMN55082.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/9-9EvergreenStYoungstownOH44512.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/8-9230WHowardStreetWarminsterPA18974.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/0-4PumpkinHillStreetAntiochTN37013.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/3-52SouthRidgeStViennaVA22180.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/5-606SilverSpearLaneDefianceOH43512.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/7-362LancasterDrOakForestIL60452.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/6-95908thLaneSeymourIN47274.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/11-8781stStLombardIL60148.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/15-351WestMountainviewLaneRowlettTX75088.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/14-218JacksonStWhitestoneNY11357.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/16-88BlueSpringAveEastLansingMI48823.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/17-8929GroveDriveEllenwoodGA30294.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/19-9CardinalCourtSaintCloudMN56301.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/18-347TarkilnHillDrMiamiFL33125.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/1-495MarshRoadPortageIN46368.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/20-94LakeshoreDriveWestDeptfordNJ08096.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/22-752NSulphurSpringsCourtMeadvillePA16335.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/25-288EastBrookStMiddletownCT06457.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/26-181SouthRiverStMainevilleOH45039.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/27-9128MammothRdParkForestIL60466.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/12-73StPaulRdStatesvilleNC28625.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/28-30CoffeeAveBowieMD20715.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/29-30NorthBriarwoodStVeniceFL34293.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/30-7501MadisonStreetStatenIslandNY10301.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/31-389PeachtreeStreetIndianapolisIN46201.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/23-62HighlandAveHolyokeMA01040.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/33-7100HarrisonDrInmanSC29349.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/37-408WhiteLaneMartinsvilleVA24112.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/36-302BerkshireStCedarRapidsIA52402.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/34-9294WildRoseAveHelenaMT59601.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/38-8491VermontAveMerrimackNH03054.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/39-71JoyRidgeStPataskalaOH43062.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/40-8124PoplarStApt262.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/41-431PennStLandOLakesFL34639.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/42-7155BerkshireStJupiterFL33458.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/44-260BELeatherwoodStFranklinMA02038.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/45-648SanPabloDrHartselleAL35640.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/47-817SageRdAnaheimCA92806.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/48-80EastThirdAvePembrokePinesFL33028.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/49-46ApplegateStTorranceCA90505.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/50-9611HomesteadRoadFairbornOH45324.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/51-87EGolfStReisterstownMD21136.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/2-7088NWildRoseAveHartfordCT06106.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/52-6SETroutStreetAllentownPA18102.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/53-12BirchpondStLosBanosCA93635.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/55-48WinchesterAveArlingtonHeightsIL60004.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/56-957NicholsLaneNewtownPA18940.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/10-62EagleDrChesapeakeVA23320.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/13-9565GlenRidgeAveManassasVA20109.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/58-961JeffersonStNorthCantonOH44720.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/59-62OldLafayetteStBiloxiMS39532.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/21-30CentralStLaVergneTN37086.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/60-9522NWCollegeAveWillingboroNJ08046.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/24-631TruselStCranstonRI02920.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/54-804NewSaddleRoadRockvilleCentreNY11570.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/62-78PleasantStWaterburyCT06705.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/61-39YukonStreetLagrangeGA30240.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/63-328RailroadDriveFondDuLacWI54935.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/64-481ClayAveGarfieldNJ07026.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/66-466ElDoradoLanePottstownPA19464.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/67-483ArgyleCourtRiversideNJ08075.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/69-75SLoganStreetLaPorteIN46350.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/70-394TunnelRdNewBedfordMA02740.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/71-467AndoverDriveWayneNJ07470.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/72-1NorthStGrotonCT06340.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/73-99HighPointDrCambridgeMA02138.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/32-9846WButtonwoodStWoosterOH44691.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/74-81GlenridgeStreetHummelstownPA17036.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/35-742ManorStationStreetSaltLakeCityUT84119.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/75-53VernonDriveHagerstownMD21740.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/77-13ArrowheadDriveBoyntonBeachFL33435.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/78-808210thRdOxonHillMD20745.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/80-29KingstonAveAugustaGA30906.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/81-55ProspectStBloomingtonIN47401.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/82-135LilacStWilkesBarrePA18702.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/84-267HarvardStreetHomesteadFL33030.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/83-943LittletonStreetBozemanMT59715.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/85-825WindsorDrMiddleburgFL32068.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/86-936WestminsterAveBataviaOH45103.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/43-62PennsylvaniaDrNorthBergenNJ07047.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/88-19SunsetAvePoughkeepsieNY12601.jpg
Downloaded https://media-cdn.tripadvisor.com/media/photo-s/09/7c/a2/1f/patagonia-hostel.jpg to /tmp/89-7410HudsonCourtPrincetonNJ08540.jpg
Downloaded https://image.shutterstock.com/image-photo/traditional-english-semidetached-house-260nw-231369511.jpg to /tmp/91-938PrinceRdWheelingWV26003.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/68-11WSmithStBlacksburgVA24060.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/65-9972CrescentStreetHobartIN46342.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/92-473BearHillAveBettendorfIA52722.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/93-8378GlenwoodDriveCummingGA30040.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/94-131SouthTowerStLafayetteIN47905.jpg
Downloaded https://i.pinimg.com/originals/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c.jpg to /tmp/95-284WagonStreetPriorLakeMN55372.jpg
Downloaded https://image.shutterstock.com/image-photo/houses-built-circa-1960-on-260nw-177959672.jpg to /tmp/96-431NorthClayStBattleGroundWA98604.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/97-7383NYorkStreetEphrataPA17522.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/46-67HeatherDrMankatoMN56001.jpg
Downloaded https://image.shutterstock.com/image-photo/big-custom-made-luxury-house-260nw-374099713.jpg to /tmp/99-7102NorthCourtClementonNJ08021.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/79-223ShadyCourtRocklinCA95677.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/76-7YorkAveGibsoniaPA15044.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/87-8531SurreyDrRegoParkNY11374.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/90-35ManorStationAveColumbiaMD21044.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/57-9373StonybrookStreetElkridgeMD21075.jpg
Downloaded https://images.adsttc.com/media/images/5e5e/da62/6ee6/7e7b/b200/00e2/medium_jpg/_fi.jpg to /tmp/98-9849ProspectRdElkhartIN46514.jpg
```

#### Validation
```
$ ls /tmp/*.jpg
/tmp/0-4PumpkinHillStreetAntiochTN37013.jpg          /tmp/27-9128MammothRdParkForestIL60466.jpg           /tmp/45-648SanPabloDrHartselleAL35640.jpg            /tmp/63-328RailroadDriveFondDuLacWI54935.jpg         /tmp/81-55ProspectStBloomingtonIN47401.jpg
/tmp/1-495MarshRoadPortageIN46368.jpg                /tmp/28-30CoffeeAveBowieMD20715.jpg                  /tmp/46-67HeatherDrMankatoMN56001.jpg                /tmp/64-481ClayAveGarfieldNJ07026.jpg                /tmp/82-135LilacStWilkesBarrePA18702.jpg
/tmp/10-62EagleDrChesapeakeVA23320.jpg               /tmp/29-30NorthBriarwoodStVeniceFL34293.jpg          /tmp/47-817SageRdAnaheimCA92806.jpg                  /tmp/65-9972CrescentStreetHobartIN46342.jpg          /tmp/83-943LittletonStreetBozemanMT59715.jpg
/tmp/11-8781stStLombardIL60148.jpg                   /tmp/3-52SouthRidgeStViennaVA22180.jpg               /tmp/48-80EastThirdAvePembrokePinesFL33028.jpg       /tmp/66-466ElDoradoLanePottstownPA19464.jpg          /tmp/84-267HarvardStreetHomesteadFL33030.jpg
/tmp/12-73StPaulRdStatesvilleNC28625.jpg             /tmp/30-7501MadisonStreetStatenIslandNY10301.jpg     /tmp/49-46ApplegateStTorranceCA90505.jpg             /tmp/67-483ArgyleCourtRiversideNJ08075.jpg           /tmp/85-825WindsorDrMiddleburgFL32068.jpg
/tmp/13-9565GlenRidgeAveManassasVA20109.jpg          /tmp/31-389PeachtreeStreetIndianapolisIN46201.jpg    /tmp/5-606SilverSpearLaneDefianceOH43512.jpg         /tmp/68-11WSmithStBlacksburgVA24060.jpg              /tmp/86-936WestminsterAveBataviaOH45103.jpg
/tmp/14-218JacksonStWhitestoneNY11357.jpg            /tmp/32-9846WButtonwoodStWoosterOH44691.jpg          /tmp/50-9611HomesteadRoadFairbornOH45324.jpg         /tmp/69-75SLoganStreetLaPorteIN46350.jpg             /tmp/87-8531SurreyDrRegoParkNY11374.jpg
/tmp/15-351WestMountainviewLaneRowlettTX75088.jpg    /tmp/33-7100HarrisonDrInmanSC29349.jpg               /tmp/51-87EGolfStReisterstownMD21136.jpg             /tmp/7-362LancasterDrOakForestIL60452.jpg            /tmp/88-19SunsetAvePoughkeepsieNY12601.jpg
/tmp/16-88BlueSpringAveEastLansingMI48823.jpg        /tmp/34-9294WildRoseAveHelenaMT59601.jpg             /tmp/52-6SETroutStreetAllentownPA18102.jpg           /tmp/70-394TunnelRdNewBedfordMA02740.jpg             /tmp/89-7410HudsonCourtPrincetonNJ08540.jpg
/tmp/17-8929GroveDriveEllenwoodGA30294.jpg           /tmp/35-742ManorStationStreetSaltLakeCityUT84119.jpg /tmp/53-12BirchpondStLosBanosCA93635.jpg             /tmp/71-467AndoverDriveWayneNJ07470.jpg              /tmp/9-9EvergreenStYoungstownOH44512.jpg
/tmp/18-347TarkilnHillDrMiamiFL33125.jpg             /tmp/36-302BerkshireStCedarRapidsIA52402.jpg         /tmp/54-804NewSaddleRoadRockvilleCentreNY11570.jpg   /tmp/72-1NorthStGrotonCT06340.jpg                    /tmp/90-35ManorStationAveColumbiaMD21044.jpg
/tmp/19-9CardinalCourtSaintCloudMN56301.jpg          /tmp/37-408WhiteLaneMartinsvilleVA24112.jpg          /tmp/55-48WinchesterAveArlingtonHeightsIL60004.jpg   /tmp/73-99HighPointDrCambridgeMA02138.jpg            /tmp/91-938PrinceRdWheelingWV26003.jpg
/tmp/2-7088NWildRoseAveHartfordCT06106.jpg           /tmp/38-8491VermontAveMerrimackNH03054.jpg           /tmp/56-957NicholsLaneNewtownPA18940.jpg             /tmp/74-81GlenridgeStreetHummelstownPA17036.jpg      /tmp/92-473BearHillAveBettendorfIA52722.jpg
/tmp/20-94LakeshoreDriveWestDeptfordNJ08096.jpg      /tmp/39-71JoyRidgeStPataskalaOH43062.jpg             /tmp/57-9373StonybrookStreetElkridgeMD21075.jpg      /tmp/75-53VernonDriveHagerstownMD21740.jpg           /tmp/93-8378GlenwoodDriveCummingGA30040.jpg
/tmp/21-30CentralStLaVergneTN37086.jpg               /tmp/4-7798PoplarStStillwaterMN55082.jpg             /tmp/58-961JeffersonStNorthCantonOH44720.jpg         /tmp/76-7YorkAveGibsoniaPA15044.jpg                  /tmp/94-131SouthTowerStLafayetteIN47905.jpg
/tmp/22-752NSulphurSpringsCourtMeadvillePA16335.jpg  /tmp/40-8124PoplarStApt262.jpg                       /tmp/59-62OldLafayetteStBiloxiMS39532.jpg            /tmp/77-13ArrowheadDriveBoyntonBeachFL33435.jpg      /tmp/95-284WagonStreetPriorLakeMN55372.jpg
/tmp/23-62HighlandAveHolyokeMA01040.jpg              /tmp/41-431PennStLandOLakesFL34639.jpg               /tmp/6-95908thLaneSeymourIN47274.jpg                 /tmp/78-808210thRdOxonHillMD20745.jpg                /tmp/96-431NorthClayStBattleGroundWA98604.jpg
/tmp/24-631TruselStCranstonRI02920.jpg               /tmp/42-7155BerkshireStJupiterFL33458.jpg            /tmp/60-9522NWCollegeAveWillingboroNJ08046.jpg       /tmp/79-223ShadyCourtRocklinCA95677.jpg              /tmp/97-7383NYorkStreetEphrataPA17522.jpg
/tmp/25-288EastBrookStMiddletownCT06457.jpg          /tmp/43-62PennsylvaniaDrNorthBergenNJ07047.jpg       /tmp/61-39YukonStreetLagrangeGA30240.jpg             /tmp/8-9230WHowardStreetWarminsterPA18974.jpg        /tmp/98-9849ProspectRdElkhartIN46514.jpg
/tmp/26-181SouthRiverStMainevilleOH45039.jpg         /tmp/44-260BELeatherwoodStFranklinMA02038.jpg        /tmp/62-78PleasantStWaterburyCT06705.jpg             /tmp/80-29KingstonAveAugustaGA30906.jpg              /tmp/99-7102NorthCourtClementonNJ08021.jpg
```
