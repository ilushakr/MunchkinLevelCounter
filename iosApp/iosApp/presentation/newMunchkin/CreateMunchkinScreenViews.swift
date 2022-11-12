//
//  CreateMunchkinScreenViews.swift
//  iosApp
//
//  Created by Крешков Илья on 11.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import sharedmainfeature

struct ColorPickerGrid: View{
    
    @ObservedObject var newMunchkin: MunchkinUI
    @Binding var presentSheet: Bool
    
    var body: some View{
        Grid(horizontalSpacing: 32, verticalSpacing: 32) {
            ForEach(0..<3) { x in
                GridRow {
                    ForEach(0..<3) { y in
                        ColorCircleButton(
                            index: x * 3 + y,
                            newMunchkin: newMunchkin,
                            presentSheet: $presentSheet
                        )
                    }
                }
            }
        }
    }
}

struct ColorCircleButton: View {
    
    let index: Int
    @ObservedObject var newMunchkin: MunchkinUI
    @Binding var presentSheet: Bool
    
    private let colors = Munchkin.Companion.shared.colorsListRGB.map{ color in
        Color(color.swiftUIColor())
    }
    
    var body: some View {
        Button {
            newMunchkin.color = colors[index]
            presentSheet.toggle()
        } label: {
            ZStack(alignment: Alignment.center){
                Circle().fill(colors[index]).frame(width: 48, height: 48)
                if(colors[index] == newMunchkin.color){
                    Image("done-done_symbol")
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(.white)
                        .frame(width: 24, height: 24)
                }
            }
        }
    }
}
