//
//  SettingsScreen.swift
//  iosApp
//
//  Created by Крешков Илья on 12.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SettingsScreen: View {
    
    @EnvironmentObject private var viewModel: SettingsViewModel
    
    var body: some View {
        List{
            SettingsOption(
                optionName: "Keep screen on",
                imageName: getOptionImageName(keepScreenOn: viewModel.keepScreenOn)
            ){
                Toggle("Keep screen on", isOn: $viewModel.keepScreenOn)
                    .labelsHidden()
            }
            
            SettingsOption(
                optionName: "Version: \(viewModel.appVersion)",
                imageName: "info-info_symbol"
            ){}
            
            Link(
                destination: URL(string: viewModel.rulesUrl)!,
                label: {
                    SettingsOption(
                        optionName: "Rules",
                        imageName: "rocket_launch-rocket_launch_symbol"
                    ){}
                }
            )
            
        }
        .navigationTitle("Settings")
    }
    
    private func getOptionImageName(keepScreenOn: Bool) -> String{
        if keepScreenOn{
            return "lightbulb_fill1_symbol"
        }
        else{
            return "lightbulb_symbol"
        }
    }
}

struct SettingsScreen_Previews: PreviewProvider {
    static var previews: some View {
        SettingsScreen()
    }
}
