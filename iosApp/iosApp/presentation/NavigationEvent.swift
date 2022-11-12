//
//  UiState.swift
//  iosApp
//
//  Created by Крешков Илья on 10.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

enum NavigationEvent{
    
    case dismissView
    
    case alert(AlertDialog)
    
    case closeAlert
}

struct AlertDialog{
    let title: String
    let subTitle: String?
    let type: AlertType
}

enum AlertType{
    case simple(SimpleAlert)
    case dual(DualAlert)
}

struct SimpleAlert{
    let buttonText: String
    let onCLick: () -> Void
}

struct DualAlert{
    let primaryButtonText: String
    let primaryAction: () -> Void
    let secondaryButtonText: String
    let secondaryAction: () -> Void
}
