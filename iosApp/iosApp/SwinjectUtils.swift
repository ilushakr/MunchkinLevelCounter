//
//  Utils.swift
//  iosApp
//
//  Created by Крешков Илья on 06.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Swinject
import sharedmainfeature

@propertyWrapper
struct Inject<I> {
    let wrappedValue: I
    init() {
        //Resolve the interface to an implementation.
        self.wrappedValue = Resolver.shared.resolve(I.self)
    }
}

class Resolver {
    static let shared = Resolver()
    
    //get the IOC container
    private var container = buildContainer()
    
    func resolve<T>(_ type: T.Type) -> T {
        container.resolve(T.self)!
    }
}

func buildContainer() -> Container {
    let container = Container()
    container.register(LocalDataProvider.self) { _ in LocalDataProviderImpl() }
    return container
}
