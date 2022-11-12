//
//  ViewUtils.swift
//  iosApp
//
//  Created by Крешков Илья on 10.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import sharedmainfeature
import UIKit

func getAlert(alertDialog: AlertDialog) -> Alert {
    
    var subTitleText: Text? = nil
    
    if let subtitle = alertDialog.subTitle{
        subTitleText = Text(subtitle)
    }
    
    switch(alertDialog.type){
        
    case .simple(let simpleAlert):
        return Alert(
            title: Text(alertDialog.title),
            message: subTitleText,
            dismissButton: .default(Text(simpleAlert.buttonText), action: {simpleAlert.onCLick()})
        )
        
    case .dual(let dualAlert):
        return Alert(
            title: Text(alertDialog.title),
            message: subTitleText,
            primaryButton: .default(Text(dualAlert.primaryButtonText), action: dualAlert.primaryAction),
            secondaryButton: .destructive(Text(dualAlert.secondaryButtonText), action: dualAlert.secondaryAction)
        )
        
    }
}

extension MunchkinUI{
    func getSexIcon() -> Image{
        switch sex {
        case "male":
            return Image("male-male_symbol")
        case "female":
            return Image("female-female_symbol")
        default:
            return Image("transgender-transgender_symbol")
        }
    }
}

extension Color {
    var components: (red: CGFloat, green: CGFloat, blue: CGFloat, opacity: CGFloat) {

        #if canImport(UIKit)
        typealias NativeColor = UIColor
        #elseif canImport(AppKit)
        typealias NativeColor = NSColor
        #endif

        var r: CGFloat = 0
        var g: CGFloat = 0
        var b: CGFloat = 0
        var o: CGFloat = 0

        guard NativeColor(self).getRed(&r, green: &g, blue: &b, alpha: &o) else {
            // You can handle the failure here as you want
            return (0, 0, 0, 0)
        }

        return (r, g, b, o)
    }
}
