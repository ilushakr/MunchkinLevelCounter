//
//  MunchkinRow.swift
//  iosApp
//
//  Created by Крешков Илья on 12.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedmainfeature

struct MunchkinRow: View{
    
    private let selectedBackgroundColor = Color(red: 238 / 255, green: 238 / 255, blue: 238 / 255)
    let munchkin: MunchkinUI
    
    @Binding var editMode: Bool
    @Binding var selected: Bool
    
    var onDelete: (MunchkinUI) -> Void = { _ in }
    let onClick: (MunchkinUI) -> Void
    
    var body: some View {
        if(selected){
            MunchkinRowItem(
                editMode: $editMode,
                munchkin: munchkin,
                onDelete: onDelete,
                onClick: onClick
            )
            .listRowBackground(selectedBackgroundColor)
        }
        else{
            MunchkinRowItem(
                editMode: $editMode,
                munchkin: munchkin,
                onDelete: onDelete,
                onClick: onClick
            )
        }
    }
    
    struct MunchkinRowItem : View{
        
        @Binding var editMode: Bool
        let munchkin: MunchkinUI
        var onDelete: (MunchkinUI) -> Void = {_ in }
        let onClick: (MunchkinUI) -> Void
        
        var body: some View{
            Button(action: {
                onClick(munchkin)
            }, label: {
                HStack{
                    ZStack{
                        Circle()
                            .fill(munchkin.color)
                            .frame(width: 56, height: 56)
                        if let firstLetter = munchkin.name.first?.uppercased(){
                            Text(String(firstLetter))
                                .foregroundColor(.white)
                                .font(.system(size: 24))
                        }
                    }.padding(.trailing, 4)
                    
                    VStack(alignment: HorizontalAlignment.leading, spacing: 4){
                        Text(munchkin.name)
                        munchkin.getSexIcon()
                            .resizable()
                            .scaledToFit()
                            .frame(height: 14)
                    }
                    
                    Spacer()
                    
                    if(editMode){
                        Button(action: {
                            onClick(munchkin)
                        }){
                            Image("edit-edit_symbol")
                        }
                        
                        Spacer().frame(width: 40)
                        
                        Button(action: {
                            onDelete(munchkin)
                        }){
                            Image("delete-delete_symbol")
                        }
                        
                        Spacer().frame(width: 20)
                    }else{
                        MunchkinCharacteristics(
                            characteristic: String(munchkin.level),
                            image: Image("bolt-bolt_symbol")
                        )
                        
                        Spacer().frame(width: 20)
                        
                        MunchkinCharacteristics(
                            characteristic: String(munchkin.strength),
                            image: Image("colorize-colorize_symbol")
                        )
                    }
                    
                }
            })
            .frame(height: 64)
        }
    }
    
    struct MunchkinCharacteristics: View{
        
        let characteristic: String
        let image: Image
        
        var body: some View{
            HStack{
                Text(characteristic)
                image
            }
        }
    }
}
