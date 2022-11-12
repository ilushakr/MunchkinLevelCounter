//
//  MunchkinUI.swift
//  iosApp
//
//  Created by Крешков Илья on 11.11.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedmainfeature
import SwiftUI

class MunchkinUI: ObservableObject, Equatable, Hashable {
    
    private(set) var id: KotlinInt?
    @Published var name: String
    @Published private(set) var level: Int
    @Published private(set) var strength: Int
    @Published var sex: String
    @Published var color: Color
    
    init(id: KotlinInt?, name: String, level: Int, strength: Int, sex: String, color: Color) {
        self.id = id
        self.name = name
        self.level = level
        self.strength = strength
        self.sex = sex
        self.color = color
    }
    
    init(munchkin: Munchkin){
        self.id = munchkin.id
        self.name = munchkin.name
        self.level = Int(munchkin.level)
        self.strength = Int(munchkin.strength)
        self.sex = munchkin.sex
        self.color = Color(munchkin.swiftUIColor())
    }
    
    func isEmptyMunchkin() -> Bool{
        return id == nil && name.isEmpty
    }
    
    func getMunchkin()-> Munchkin{
        return Munchkin(
            id: self.id,
            name: self.name,
            level: Int32(self.level),
            strength: Int32(self.strength),
            sex: self.sex,
            colorRGB: RGBColor_(
                red: self.color.components.red * 255,
                green: self.color.components.green * 255,
                blue: self.color.components.blue * 255)
        )
    }
    
    func copy() -> MunchkinUI{
        return MunchkinUI(
            id: self.id,
            name: self.name,
            level: self.level,
            strength: self.strength,
            sex: self.sex,
            color: self.color
        )
    }
    
    func copy(id: KotlinInt?) -> MunchkinUI{
        return MunchkinUI(
            id: id,
            name: self.name,
            level: self.level,
            strength: self.strength,
            sex: self.sex,
            color: self.color
        )
    }
    
    func copy(
        name: String? = nil,
        level: Int? = nil,
        strength: Int? = nil,
        sex: String? = nil,
        color: Color? = nil
    ) -> MunchkinUI{
        return MunchkinUI(
            id: self.id,
            name: name ?? self.name,
            level: level ?? self.level,
            strength: strength ?? self.strength,
            sex: sex ?? self.sex,
            color: color ?? self.color
        )
    }
    
    static func getEmptyMunchkin() -> MunchkinUI{
        return MunchkinUI(id: nil, name: "", level: -1, strength: -1, sex: "", color: .black)
    }
    
    func totalStrength() -> Int{
        return self.level + self.strength
    }
    
    static func == (lhs: MunchkinUI, rhs: MunchkinUI) -> Bool {
        return lhs.id == rhs.id &&
        lhs.name == rhs.name &&
        lhs.level == rhs.level &&
        lhs.strength == rhs.strength &&
        lhs.sex == rhs.sex &&
        lhs.color == rhs.color
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
        hasher.combine(name)
        hasher.combine(level)
        hasher.combine(strength)
        hasher.combine(sex)
        hasher.combine(color)
    }
    
}
