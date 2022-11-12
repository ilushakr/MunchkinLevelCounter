//
//  SettingsViewModel.swift
//  iosApp
//
//  Created by Крешков Илья on 12.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit
import sharedmainfeature

class SettingsViewModel: ObservableObject{
    
    @Inject
    private var localDataProviderImpl: LocalDataProvider
    
    //    private let localDataProviderImpl = LocalDataProviderImpl()
    
    init(){
        keepScreenOn = localDataProviderImpl.getKeepScreenOn()
        UIApplication.shared.isIdleTimerDisabled = keepScreenOn
    }
    
    @Published var keepScreenOn = false{
        willSet{
            localDataProviderImpl.putKeepScreenOn(value: newValue)
            UIApplication.shared.isIdleTimerDisabled = newValue
        }
    }
    
    let appVersion = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as! String
    
    let rulesUrl = "https://www.wikihow.com/Play-Munchkin"
}
