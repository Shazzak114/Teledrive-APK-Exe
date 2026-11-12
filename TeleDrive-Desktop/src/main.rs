use eframe::egui;

#[derive(Default)]
struct TeleDriveApp;

impl eframe::App for TeleDriveApp {
    fn update(&mut self, context: &egui::Context, _frame: &mut eframe::Frame) {
        egui::CentralPanel::default().show(context, |ui| {
            ui.heading("TeleDrive Desktop");
            ui.label("Desktop application source is not included in this repository yet.");
            ui.label("This window is only a build scaffold; vault and Telegram features are not implemented.");
        });
    }
}

fn main() -> eframe::Result<()> {
    eframe::run_native(
        "TeleDrive Desktop",
        eframe::NativeOptions::default(),
        Box::new(|_creation_context| Box::<TeleDriveApp>::default()),
    )
}
