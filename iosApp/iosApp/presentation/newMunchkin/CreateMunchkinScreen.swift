//
//  CreateMunchkinScreen.swift
//  iosApp
//
//  Created by Крешков Илья on 03.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import sharedmainfeature
import Foundation

struct CreateMunchkinScreen: View {
    
    init(currentMunchkin: MunchkinUI){
        self.newMunchkin = currentMunchkin.copy()
        self.currentMunchkin = currentMunchkin.copy()
        
    }
    
    @Environment(\.presentationMode) var presentationMode
    
    @EnvironmentObject var viewModel: MunchkinViewModel
    @ObservedObject var newMunchkin: MunchkinUI
    private var currentMunchkin: MunchkinUI
    
    @State var alertDialog: AlertDialog? = nil
    @State var presentSheet = false
    @State private var showingAlert = false
    
    private let availableSexes = Munchkin.Companion.shared.getAvailableSexes()
    private let availableColors = Munchkin.Companion.shared.colorsListRGB
    
    var body: some View {
        
        return VStack(alignment: HorizontalAlignment.center, spacing: 36){
            
            TextField("Name", text: $newMunchkin.name)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            Picker(newMunchkin.sex, selection: $newMunchkin.sex){
                ForEach(availableSexes, id: \.self) { sex in
                    Text(sex).tag(sex)
                }
            }
            .pickerStyle(.segmented)
            
            ZStack(alignment: Alignment.center){
                newMunchkin.getSexIcon()
                    .resizable()
                    .scaledToFit()
            }
            .frame(height: 24)
            
            Button(
                action: {
                    presentSheet.toggle()
                },
                label: {
                    HStack{
                        Text("Chose color:")
                        Circle()
                            .fill(newMunchkin.color)
                            .frame(width: 36, height: 36)
                    }
                }
            )
            
            Spacer()
        }
        .padding(16)
        .navigationTitle("New munckin")
        .toolbar{
            ToolbarItem{
                Button(action: {
                    viewModel.instert(currentMunchkin: currentMunchkin, newMunchin: newMunchkin)
                }){
                    Image("done-done_symbol")
                }
            }
        }
        .sheet(isPresented: $presentSheet) {
            ColorPickerGrid(
                newMunchkin: newMunchkin,
                presentSheet: $presentSheet
            )
            .presentationDetents([.medium])
        }
        .alert(isPresented: Binding<Bool>(get: {alertDialog != nil}, set: {_ in })) {
            getAlert(alertDialog: alertDialog!)
            
        }
        .onReceive(viewModel.navigationEventPublisher){ navigationEvent in
            switch navigationEvent {
                
            case .dismissView:
                presentationMode.wrappedValue.dismiss()
                
            case .alert(let alertDialog):
                self.alertDialog = alertDialog
                
            case .closeAlert:
                self.alertDialog = nil
            }
        }
        .onReceive(viewModel.$munchkinList){ list in
            if(list.contains{ $0 == newMunchkin.copy(id: $0.id) }){
                presentationMode.wrappedValue.dismiss()
            }
        }
    }
    
}

struct CreateMunchkinScreen_Previews: PreviewProvider {
    static var previews: some View {
        CreateMunchkinScreen(currentMunchkin: MunchkinUI(munchkin: Munchkin.Companion.shared.getEmptyMunchkin()))
    }
}
