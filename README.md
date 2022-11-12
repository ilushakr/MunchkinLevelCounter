# Munchkin Level Counter
Kotlin multi platform app for count levels of u'r munchkins

# Techs
Koin
Swinject
SQLDelight
Compose
SwiftUi

# To run app on iphone:
build the project
run ./gradlew :sharedmainfeature:assembleXCFramework
open xcode with this project and go to project preferences -> General -> Frameworks, Libraries, and Embeded Content
find generated on step 2 framework at project_root/sharedmain/build/XCFrameworks/debug or release/ and add it to xcode
now u can use both modules (sharedmainfeature and shared module with core logic)in the ios app

# Screenshots
Not so fancy app but its only a start of my journey. One small step for man(me), one giant leap for mankind(also me)
## Android
<img height="358" alt="image" src="https://user-images.githubusercontent.com/37439482/201497931-11698699-4b28-4636-9cb0-f05aad78ef55.png">
<img height="358" alt="image" src="https://user-images.githubusercontent.com/37439482/201497933-69bae87b-b0d2-47f0-8edf-8445dcda9ba1.png">
<img height="358" alt="image" src="https://user-images.githubusercontent.com/37439482/201497935-377810da-390c-41ae-a280-f5c731ce8aab.png">
<img height="358" alt="image" src="https://user-images.githubusercontent.com/37439482/201497938-ed4545a0-729c-455a-9bb0-a08176847ad4.png">

## iOS
![Simulator Screen Shot - iPhone 13 mini - 2022-11-13 at 01 47 37](https://user-images.githubusercontent.com/37439482/201497949-a2b2a962-34ac-48c0-b9ff-32f113eca37f.png)
![Simulator Screen Shot - iPhone 13 mini - 2022-11-13 at 01 48 17](https://user-images.githubusercontent.com/37439482/201497951-ae4c8d7f-2ac8-412a-a148-50377298053c.png)
![Simulator Screen Shot - iPhone 13 mini - 2022-11-13 at 01 52 38](https://user-images.githubusercontent.com/37439482/201497952-bd200692-a063-42f9-9a8f-277b5707f250.png)
![Simulator Screen Shot - iPhone 13 mini - 2022-11-13 at 01 52 54](https://user-images.githubusercontent.com/37439482/201497953-d79826c5-551e-4d29-ad49-f9a392cba882.png)
