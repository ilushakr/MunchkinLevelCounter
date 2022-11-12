//
//  MunchkinViewModel.swift
//  iosApp
//
//  Created by Крешков Илья on 23.10.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Combine
import Foundation
import SwiftUI
import sharedmainfeature



@MainActor  class MunchkinViewModel: ObservableObject {
    
    let navigationEventPublisher = PassthroughSubject<NavigationEvent, Never>()
    
    private let munchkinController = MunchkinControllerCompanion.shared.doInitController()
    
    @Published var munchkinList = [MunchkinUI]()
    
    @Published var isEmptyList = false
    
    init(){
        
        munchkinController.getMunchkins()
        munchkinController.onChange{ state in
            let munchkinState = state as! MunchkinStore.MunchkinState
            
            if let data = munchkinState.data {
                
                let dataUI = data.mapToUI()
                self.munchkinList = dataUI
                self.isEmptyList = self.munchkinList.isEmpty
            }
            if munchkinState.error != nil {
                self.navigationEventPublisher.send(
                    NavigationEvent.alert(
                        AlertDialog(
                            title: munchkinState.error?.message ?? "Error",
                            subTitle: "Please, choose another name",
                            type: AlertType.simple(
                                SimpleAlert(
                                    buttonText: "Got it!",
                                    onCLick: {
                                        self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                    }
                                )
                            )
                        )
                    )
                )
                print(munchkinState.error?.message ?? "unknown error")
            }
        }
    }
    
    func instert(currentMunchkin: MunchkinUI, newMunchin: MunchkinUI){
        if(!newMunchin.isEmptyMunchkin() && newMunchin == currentMunchkin){
            navigationEventPublisher.send(NavigationEvent.dismissView)
            return
        }
        
        if(newMunchin.name.isEmpty){
            navigationEventPublisher.send(
                NavigationEvent.alert(
                    AlertDialog(
                        title: "Name can\'t be blank",
                        subTitle: "Please, choose another name",
                        type: AlertType.simple(
                            SimpleAlert(
                                buttonText: "Got it!",
                                onCLick: {
                                    self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                }
                            )
                        )
                    )
                )
            )
        }
        else{
            if(currentMunchkin.isEmptyMunchkin()){
                munchkinController.insertMunchkin(munchkin: newMunchin.getMunchkin())
            }
            else{
                munchkinController.updateMunchkinById(munchkin: newMunchin.getMunchkin())
            }
        }
    }
    
    func delete(munchinUI: MunchkinUI){
        
        let munchin = munchinUI.getMunchkin()
        
        if let id = munchin.id {
            navigationEventPublisher.send(
                NavigationEvent.alert(
                    AlertDialog(
                        title: "Are you sure you want to delete \(munchin.name)",
                        subTitle: nil,
                        type: AlertType.dual(
                            DualAlert(
                                primaryButtonText: "Cancel",
                                primaryAction: {
                                    self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                },
                                secondaryButtonText: "Delete",
                                secondaryAction: {
                                    self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                    self.munchkinController.deleteMunchkinById(munchkinId: Int32(truncating: id))
                                }
                            )
                        )
                    )
                )
            )
        }
    }
    
    func killMunchkin(munchkinUI: MunchkinUI){
        navigationEventPublisher.send(
            NavigationEvent.alert(
                AlertDialog(
                    title: "Are you sure you want to kill \(munchkinUI.name)",
                    subTitle: nil,
                    type: AlertType.dual(
                        DualAlert(
                            primaryButtonText: "Cancel",
                            primaryAction: {
                                self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                            },
                            secondaryButtonText: "Kill",
                            secondaryAction: {
                                self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                self.updateMunchkin(munchkinUI: munchkinUI)
                            }
                        )
                    )
                )
            )
        )
    }
    
    func updateMunchkin(munchkinUI: MunchkinUI){
        munchkinController.updateMunchkinById(munchkin: munchkinUI.getMunchkin())
    }
    
    func killAllMunchkins(){
        navigationEventPublisher.send(
            NavigationEvent.alert(
                AlertDialog(
                    title: "Are you sure you want to kill all munchkins",
                    subTitle: nil,
                    type: AlertType.dual(
                        DualAlert(
                            primaryButtonText: "Cancel",
                            primaryAction: {
                                self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                            },
                            secondaryButtonText: "Kill",
                            secondaryAction: {
                                self.navigationEventPublisher.send(NavigationEvent.closeAlert)
                                self.munchkinList.forEach{ munchkin in
                                    self.updateMunchkin(munchkinUI: munchkin.copy(level: 1, strength: 0))
                                }
                            }
                        )
                    )
                )
            )
        )
    }
}

extension [Munchkin] {
    func mapToUI() -> [MunchkinUI]{
        return self.map{munchkin in MunchkinUI(munchkin: munchkin)}
    }
}
