//
//  EmptySimpleListView.swift
//  iosApp
//
//  Created by Крешков Илья on 26.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import sharedmainfeature

struct EmptySimpleListView: View{
    
    var body: some View{
        VStack{
            Image("sentiment_dissatisfied")
                .resizable()
                .frame(width: 50.0, height: 50.0)
            
            Text("No munchkins yet")
        }
    }
    
}

struct FloatingActionButton: View{
    
    let onClick: () -> Void
    
    var body: some View{
        Button(action: {
            onClick()
        }){
            ZStack(alignment: .center){
                Image("add-add_symbol")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(.white)
                    .padding(20)
            }
            .background(baseMunchkinColor)
            .clipShape(Circle())
            .frame(width: 56, height: 56)
        }
        .padding(20)
    }
}

struct ToolbarItems: View{
    
    @EnvironmentObject var viewModel: MunchkinViewModel
    
    @Binding var editMode: Bool
    @Binding var currentScreen: String?
    @Binding var isEmptyData: Bool
    @Binding var canRestore: Bool
    
    let onRestoreClick: () -> Void
    
    var body: some View{
        if(editMode){
            Button(action: {
                editMode.toggle()
            }){
                Image("done-done_symbol")
                
            }
        }else{
            HStack(spacing: 16){
                if(!isEmptyData){
                    Button(action: {
                        viewModel.killAllMunchkins()
                    }){
                        Image("settings_backup_restore-settings_backup_restore_symbol")
                    }
                    .disabled(!canRestore)
                    
                    Button(action: {
                        editMode.toggle()
                    }){
                        Image("edit-edit_symbol")
                    }
                }
                
                Button(action: {
                    currentScreen = "settings"
                }){
                    Image(systemName: "gearshape")
                }
            }
        }
    }
}

