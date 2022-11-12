//
//  DetailedListScreen.swift
//  iosApp
//
//  Created by Крешков Илья on 11.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedmainfeature

struct DetailedListScreen: View {
    
    @State var selectedId: Int
    
    @EnvironmentObject var viewModel: MunchkinViewModel
    
    var body: some View {
        VStack(alignment: HorizontalAlignment.center, spacing: 24){
            Text("Strength").font(.system(size: 24))
            
            Text(String(currentMunchkin.totalStrength())).font(.system(size: 56))
            
            Button(action: {
                viewModel.updateMunchkin(munchkinUI: currentMunchkin.copy(sex: toggleSex(munchkinUI: currentMunchkin)))
            }, label: {
                currentMunchkin.getSexIcon()
                    .resizable()
                    .scaledToFit()
                    .frame(height: 24)
            })
            
            HStack{
                MunchkinCharacteristic(
                    characteristicName: "Level",
                    characteristicValue: currentMunchkin.level,
                    lowerBound: 1,
                    upperBound: 10,
                    onClick: {value in
                        viewModel.updateMunchkin(munchkinUI: currentMunchkin.copy(level: value))
                    }
                ).frame(maxWidth: .infinity)
                
                MunchkinCharacteristic(
                    characteristicName: "Strength",
                    characteristicValue: currentMunchkin.strength,
                    onClick: {value in
                        viewModel.updateMunchkin(munchkinUI: currentMunchkin.copy(strength: value))
                    }
                ).frame(maxWidth: .infinity)
            }
            
            List{
                ForEach(viewModel.munchkinList, id: \.self){ item in
                    MunchkinRow(
                        munchkin: item,
                        editMode: Binding<Bool>(get: {false}, set: {_ in}),
                        selected: Binding<Bool>(get: { item.id as! Int == selectedId }, set: {_ in}),
                        onDelete: { _ in },
                        onClick: { munchkin in
                            self.selectedId = munchkin.id as! Int
                        }
                    )
                }
            }
            
            .listStyle(.plain)
            .shadow(color: Color.gray.opacity(0.5), radius: 2, x: 0, y: -2)
            .frame(maxHeight: .infinity)
        }
        .onReceive(viewModel.navigationEventPublisher){ navigationEvent in
            
        }
        .navigationTitle(currentMunchkin.name)
        .toolbar{
            ToolbarItemGroup(placement: .navigationBarTrailing) {
                Button(action: {
                    viewModel.killMunchkin(munchkinUI: currentMunchkin.copy(level: 1, strength: 0))
                }){
                    Image("skull-skull_fill1_symbol")
                }.disabled(currentMunchkin.totalStrength() == 1)
            }
        }
        .navigationBarTitleDisplayMode(.large)
    }
    
    private var currentMunchkin: MunchkinUI {
        return viewModel.munchkinList.first{ $0.id as! Int == selectedId} ?? MunchkinUI.getEmptyMunchkin()
    }
    
    private func toggleSex(munchkinUI: MunchkinUI) -> String{
        switch(munchkinUI.sex){
        case "male":
            return "female"
            
        case "female":
            return "male"
            
        default:
            return "transgender"
        }
    }
}
