import SwiftUI
import sharedmainfeature
import Swinject


let baseMunchkinColor = Color(red: 117/255, green: 59/255, blue: 30/255)

@main
struct iOSApp: App {
    
    @StateObject private var settingsViewModel = SettingsViewModel()
    
    init() {
        KoinHelper().doInitKoin()
        
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(settingsViewModel)
        }
    }
}
