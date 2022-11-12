import SwiftUI
import sharedbase
import sharedmainfeature


struct ContentView: View {
    
    @StateObject private var viewModel = MunchkinViewModel()
    
    // States
    @State private var currentScreen: String? = nil
    @State private var editMode = false
    @State private var selectedMunchkin: MunchkinUI? = nil
    @State var alertDialog: AlertDialog? = nil
    
    @State private var createMunchkinScreen: CreateMunchkinScreen? = nil
    @State private var detailedListScreen: DetailedListScreen? = nil
    
    var body: some View {
        return NavigationView{
            ZStack{
                NavigationLink(destination: SettingsScreen(), tag: "settings", selection: $currentScreen){ EmptyView() }
                NavigationLink(destination: createMunchkinScreen, tag: "create", selection: $currentScreen){ EmptyView() }
                NavigationLink(destination: detailedListScreen, tag: "detailed_screen", selection: $currentScreen){ EmptyView() }
                
                if viewModel.munchkinList.isEmpty {
                    EmptySimpleListView().onAppear {
                        editMode = false
                    }
                }
                else{
                    List{
                        Section{
                            ForEach(viewModel.munchkinList, id: \.self){ item in
                                MunchkinRow(
                                    munchkin: item,
                                    editMode: $editMode,
                                    selected: Binding<Bool>(get: { false }, set: {_ in}),
                                    onDelete: { munchkin in
                                        viewModel.delete(munchinUI: munchkin)
                                    },
                                    onClick: { munchkin in
                                        if(editMode){
                                            createMunchkinScreen = CreateMunchkinScreen(currentMunchkin: munchkin)
                                            currentScreen = "create"
                                        }
                                        else{
                                            detailedListScreen = DetailedListScreen(
                                                selectedId: munchkin.id as! Int
                                            )
                                            currentScreen = "detailed_screen"
                                        }
                                    }
                                )
                            }
                        } footer: { Spacer().frame(height: 88) }
                    }.listStyle(.plain)
                }
                
                VStack{
                    Spacer()
                    HStack{
                        Spacer()
                        FloatingActionButton{
                            createMunchkinScreen = CreateMunchkinScreen(currentMunchkin: MunchkinUI(munchkin: Munchkin.Companion.shared.getEmptyMunchkin()))
                            currentScreen = "create"
                        }
                    }
                }
            }
            .navigationTitle("Munchkins")
            .toolbar {
                ToolbarItemGroup(placement: .navigationBarTrailing) {
                    ToolbarItems(
                        editMode: $editMode,
                        currentScreen: $currentScreen,
                        isEmptyData: Binding<Bool>(get: { viewModel.munchkinList.isEmpty }, set: {_ in }),
                        canRestore: Binding<Bool>(get: { viewModel.munchkinList.canRestore() }, set: { _ in })
                    ){
                        
                    }
                }
            }
            .navigationBarTitleDisplayMode(.inline)
        }
        .alert(isPresented: Binding<Bool>(get: {alertDialog != nil}, set: {_ in })){
            getAlert(alertDialog: alertDialog!)
        }
        .onReceive(viewModel.navigationEventPublisher){ navigationEvent in
            switch navigationEvent {
                
            case .alert(let alertDialog):
                self.alertDialog = alertDialog
                
            case .closeAlert:
                self.alertDialog = nil
                
            default:
              break
                
            }
        }
        .environmentObject(viewModel)
        
    }
    
}

extension Array where Element == MunchkinUI {
    func canRestore() -> Bool {
        if(self.isEmpty) {
            return false
        }
        
        if self.allSatisfy({$0.level == 1 && $0.strength == 0}){
            return false
        } else {
            return true
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
