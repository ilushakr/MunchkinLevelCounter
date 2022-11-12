//
//  DetailedListScreenViews.swift
//  iosApp
//
//  Created by Крешков Илья on 12.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MunchkinCharacteristic: View {
    
    let characteristicName: String
    let characteristicValue: Int
    var lowerBound: Int = Int.min
    var upperBound: Int = Int.max
    let onClick: (Int) -> Void
    
    var body: some View {
        VStack(alignment: .center, spacing: 16){
            Text(characteristicName).font(.system(size: 24))
            Text(String(characteristicValue)).font(.system(size: 32))
            
            HStack{
                
                Spacer()
                
                Button(action: {
                    onClick(characteristicValue - 1)
                }, label: {
                    Image("arrow_drop_down-arrow_drop_down_symbol")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 10)
                })
//                .frame(maxWidth: .infinity)
                .disabled(characteristicValue <= lowerBound)
                
                Spacer()
                
                Button(action: {
                    onClick(characteristicValue + 1)
                }, label: {
                    Image("arrow_drop_up-arrow_drop_up_symbol")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 10)
                })
//                .frame(maxWidth: .infinity)
                .disabled(characteristicValue >= upperBound)
                
                Spacer()
                
            }
        }
    }
}

struct DetailedListScreenViews_Previews: PreviewProvider {
    static var previews: some View {
        MunchkinCharacteristic(characteristicName: "name", characteristicValue: 1, onClick: {_ in})
    }
}
