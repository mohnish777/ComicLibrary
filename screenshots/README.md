# Screenshots Guide

## 📱 How to Add Screenshots

### Step 1: Take Screenshots from Your App

You need to capture 3 screenshots:

1. **library_screen.png** - The Library/Search screen showing:
   - Search bar with a query
   - List of movie results
   - Movie cards with posters

2. **movie_details.png** - The Movie Details screen showing:
   - Movie backdrop image
   - Movie poster and information
   - Rating, release date, overview
   - "Add to My Collection" button

3. **collection_screen.png** - The Collection screen showing:
   - Saved movies in your collection
   - Expandable card with notes
   - Notes section with add/delete functionality

### Step 2: Capture Screenshots

**Option A: Using Android Emulator**
1. Run your app in Android Studio emulator
2. Navigate to each screen
3. Click the camera icon in the emulator toolbar
4. Screenshots will be saved to your desktop

**Option B: Using Physical Device**
1. Run your app on your Android device
2. Take screenshots (usually Power + Volume Down)
3. Transfer screenshots to your computer via USB or cloud

**Option C: Using ADB (Android Debug Bridge)**
```bash
# Take screenshot
adb shell screencap -p /sdcard/screenshot.png

# Pull to computer
adb pull /sdcard/screenshot.png ./screenshots/library_screen.png
```

### Step 3: Optimize Screenshots

For best results:
- **Recommended size**: 1080x2400 pixels (or your device's native resolution)
- **Format**: PNG (better quality) or JPG
- **File size**: Keep under 500KB each for faster loading

You can resize using:
- Online tools: [TinyPNG](https://tinypng.com/), [Squoosh](https://squoosh.app/)
- Command line: `convert library_screen.png -resize 1080x2400 library_screen.png`

### Step 4: Add to Project

1. Save your screenshots in this folder with these exact names:
   - `library_screen.png`
   - `movie_details.png`
   - `collection_screen.png`

2. Commit to Git:
   ```bash
   git add screenshots/
   git commit -m "Add app screenshots"
   git push
   ```

### Step 5: Verify

Open your README.md on GitHub to see the screenshots displayed!

---

## 🎨 Tips for Great Screenshots

- Use a clean, populated state (not empty screens)
- Show real movie data (search for popular movies like "Harry Potter", "Avengers", etc.)
- For Collection screen, add 2-3 movies with some notes
- Ensure good lighting/contrast if using physical device
- Remove any personal information
- Use the same device/emulator for all screenshots for consistency

---

## Current Status

- [ ] library_screen.png
- [ ] movie_details.png
- [ ] collection_screen.png

Once you add the screenshots, check off the boxes above!

