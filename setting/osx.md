# OSX

## Setup

### Setting

- Appearance
  - Dark
- Dock
  - Position on screen : Right
  - Size : 33%
  - Magnification : 50%
  - Check automatically hide and show the dock
- Sound
  - Sound Effects -> Alert volume to 0
- Keyboard
  - Modifier Keys
    - Caps Lock -> Control
    - ~~External keyboard only~~ -> Karabiner Virtual Keyboard로 대체
      - Control -> Control
      - Option -> Command
      - Command -> Optional
  - Shortcut
    - Input Sources
      - ~~Select the previous input source : Option + Space (⌥ + Space)~~ -> Karabiner Virtual Keyboard로 대체
    - Mission Control
      - [ ] Show Desktop
  - Function Keys : ${customizing}
- Trackpad
  - [] Tap to click
  - Tracking speed : 6
- Battery
  - Turn display off after 5 minutes
  - [ ] Slightly dim the display while on battery power
- Display
  - Scaled -> Larger Text랑 Default 사이

### Downloads

Utils

- [Scroll Reverser](https://pilotmoon.com/scrollreverser/) : Separate mouse and trackpad settings
  - Scrolling
    - Scrolling Axes
      - [X] Reverse Vertical
      - [X] Reverse Horizontal
    - Scroll Direction (Before this, set mouse scroll direction to natural)
      - [ ] Reverse Trackpad
      - [X] Reverse Mouse
    - App
      - [X] Start at login
      - [X] Shot in menu bar
- [Karabiner](https://karabiner-elements.pqrs.org/) : Virtual keyboard
  - Karabiner Elements Preferences
    - Simple modifications
      - Apple Internal Keyboard
        - right_command to **F18**
      - External keyboard (eg. Realforce)
        - left_command to left_option
        - left_option to left_command
        - right_command to right_option
        - right_option to **F18**
    - Function keys
      - For all devices
        - [X] Use all F1, F2, etc as standard function keys
    - Devices
      - [X] Apple Internal Keyboard
      - [X] External keyboard (eg. Realforce)
  - System Preference
    - Keyboard -> Keyboard -> Modifier Keys
      - Caps Lock -> Control
    - Shortcuts -> Input Sources
      - Select the previous input source to **F18** (오른쪽 command key 누르기)
    - Input Sources
      - [ ] Use the Caps Lock key to switch to and from ABC
  - see also
    - https://ssossoblog.tistory.com/54
- [Alfred](https://www.alfredapp.com) : 알프레드
  - System Preference
    - Keyboard Shortcuts
      - Spotlight
        - [ ] Show Spotlight search
  - Alfred Hotkey: `command + Space`
- [Spectacle](https://www.spectacleapp.com/) : A Window Manager
  - [ ] Launch Spectacle at login
- [RunCat](https://apps.apple.com/kr/app/runcat/id1429033973?mt=12) : Monotoring tool
- [Hammerspoon](http://www.hammerspoon.org/) : Macro tool for mac.
  - Behavior
    - [X] Launch Hammerspoon at login
    - [X] Check for updates
    - [ ] Show dock icon
    - [X] Show menu icon
    - [ ] Keep Console window on top
    - [ ] Send crash data (require restart)
  - vim hangul setting
    - Open Config -> Put code to Config -> Reload Config
      ```lua
      -- key mapping for vim
      -- Convert input soruce as English and sends 'escape' if inputSource is not English.
      -- Sends 'escape' if inputSource is English.
      -- key bindding reference --> https://www.hammerspoon.org/docs/hs.hotkey.html
      local inputEnglish = "com.apple.keylayout.ABC"
      local esc_bind

      function convert_to_eng_with_esc()
        local inputSource = hs.keycodes.currentSourceID()
        if not (inputSource == inputEnglish) then
          hs.eventtap.keyStroke({}, 'right')
          hs.keycodes.currentSourceID(inputEnglish)
        end
        esc_bind:disable()
        hs.eventtap.keyStroke({}, 'escape')
        esc_bind:enable()
      end

      esc_bind = hs.hotkey.new({}, 'escape', convert_to_eng_with_esc):enable()
      ```
    - [see also](https://humblego.tistory.com/10)
- [(IINA)](https://iina.io/) : Open source music player written in native Swift language
- ([gif brewery 3 by gfycat)](https://apps.apple.com/kr/app/gif-brewery-3-by-gfycat/id1081413713?mt=12) : gif generator
  - Video location : `~/Movies/GIF Brewery 3`

Dev

- [Iterm2](https://iterm2.com)
- [Brew](https://brew.sh/index_ko)
- [Intellij](https://www.jetbrains.com/idea/download/#section=mac)
  - [setting](./intellij.md)
- [vscode](https://code.visualstudio.com/)
  - [setting](./vscode.md)
- [install language](./language-installation.md)
- [Postman](https://www.postman.com/downloads/)

Browser

- [Chrome](https://www.google.com/chrome/)
  - [setting](./chrome.md)
- [Whale](https://whale.naver.com/en/download/mac/)
  - [setting](./whale.md)
- [(Brave)](https://brave.com/?ref=xwv588)
  - [setting](./brave.md)
- [(Firefox)](https://www.mozilla.org/en-US/firefox/new/)
  - [setting](./firefox.md)

Docs

- [Acrobat Pdf Reader](https://get.adobe.com/reader/)
  - [setting](./pdf-reader.md)

Etc

- [KakaoTalk](https://www.kakaocorp.com/page/service/service/KakaoTalk?lang=ko)

See also

https://link.medium.com/1qxMH7jnMab

## Etc

Make workspace dir & pin it to the finder

## Shortcuts

## Tips

### Set default system jdk version

```sh
> cd /Library/Java/JavaVirtualMachines/zulu-8.jdk
> mv Info.plist Info.plist.disabled
```

### 취소선 단축키

https://www.clien.net/service/board/cm_mac/13752933

### 동영상 편집

- QuickTimePlayer 사용해서 Split Clip (command + Y)을 활용해서 중간 중간 자른 후 저장
- Mp4 변환의경우 imovie 사용

### Setting default java version

[osx system java version](https://stackoverflow.com/questions/21964709/how-to-set-or-change-the-default-java-jdk-version-on-os-x)
