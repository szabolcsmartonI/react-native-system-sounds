// RNSystemSounds.m

#import "RNSystemSounds.h"
#import <AudioToolbox/AudioToolbox.h>
#import <AVFoundation/AVFoundation.h>

@implementation RNSystemSounds

RCT_EXPORT_MODULE();

- (instancetype)init {
    self = [super init];
    if (self) {
        [self configureAudioSession];
    }
    return self;
}

- (void)configureAudioSession {
    AVAudioSession *audioSession = [AVAudioSession sharedInstance];
    NSError *error = nil;
    
    // Az audio session kategória beállítása
    [audioSession setCategory:AVAudioSessionCategoryPlayback
                  withOptions:AVAudioSessionCategoryOptionDefaultToSpeaker
                        error:&error];
    
    if (error) {
        NSLog(@"Error setting audio session category: %@", error);
        return;
    }
    
    // Audio session aktiválása
    [audioSession setActive:YES error:&error];
    
    if (error) {
        NSLog(@"Error activating audio session: %@", error);
        return;
    }
}

RCT_EXPORT_METHOD(playSystemSound:(NSInteger)soundID) {
    // Rendszerhang lejátszása
    AudioServicesPlaySystemSound(soundID);
}

@end
