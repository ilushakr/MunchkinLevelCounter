//
//  LocalDataProviderImpl.swift
//  iosApp
//
//  Created by Крешков Илья on 13.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedmainfeature

class LocalDataProviderImpl: LocalDataProvider{
    
    private let defaults = UserDefaults.standard
    
    func getKeepScreenOn() -> Bool {
        return defaults.bool(forKey: LocalDataProviderImpl.IS_SCREEN_ON)
    }
    
    func putKeepScreenOn(value: Bool) {
        defaults.set(value, forKey: LocalDataProviderImpl.IS_SCREEN_ON)
    }
    
    private static let IS_SCREEN_ON = "IS_SCREEN_ON"
}
