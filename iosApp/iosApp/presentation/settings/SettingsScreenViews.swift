//
//  SettingsScreenViews.swift
//  iosApp
//
//  Created by Крешков Илья on 12.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SettingsOption<Content: View>: View {
    
    let optionName: String
    let imageName: String
    @ViewBuilder var content: () -> Content
    
    var body: some View {
        HStack(alignment: VerticalAlignment.center, spacing: 24){
            Image(imageName)
            
            Text(optionName)
            
            Spacer()
            
            content()
            
        }
        .frame(height: 64)
    }
    
}
