package org.bone.ircballoon

import org.eclipse.swt.widgets.{List => SWTList, _}
import org.eclipse.swt.layout._
import org.eclipse.swt.events._
import org.eclipse.swt.graphics._
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.custom.StackLayout

import org.eclipse.swt._

object Preference extends SWTHelper
{
    import java.util.prefs.Preferences

    val preference = Preferences.userNodeForPackage(Preference.getClass)

    def read(setting: BlockSetting)
    {
        setting.locationX.setText(preference.getInt("BlockX", 100).toString)
        setting.locationY.setText(preference.getInt("BlockY", 100).toString)
        setting.width.setText(preference.getInt("BlockWidth", 200).toString)
        setting.height.setText(preference.getInt("BlockHeight", 400).toString)

        val bgColor = new Color(
            Display.getDefault, 
            preference.getInt("BlockBGRed", 0),
            preference.getInt("BlockBGGreen", 0),
            preference.getInt("BlockBGBlue", 0)
        )

        val fontColor = new Color(
            Display.getDefault,
            preference.getInt("BlockFontRed", 255),
            preference.getInt("BlockFontGreen", 255),
            preference.getInt("BlockFontBlue", 255)
        )

        val borderColor = new Color(
            Display.getDefault,
            preference.getInt("BlockBorderRed", 255),
            preference.getInt("BlockBorderGreen", 255),
            preference.getInt("BlockBorderBlue", 255)
        )

        setting.bgColor = bgColor
        setting.fontColor = fontColor
        setting.borderColor = borderColor
        setting.bgButton.setText(bgColor)
        setting.fgButton.setText(fontColor)
        setting.borderButton.setText(borderColor)

        val font = new Font(
            Display.getDefault, 
            preference.get("BlockFontName", MyFont.DefaultFontName),
            preference.getInt("BlockFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BlockFontStyle", MyFont.DefaultFontStyle)
        )

        setting.messageFont = font
        setting.fontButton.setText(font)

        val transparent = preference.getInt("BlockTransparent", 20)
        setting.transparentScale.setSelection(transparent)
        setting.transparentLabel.setText(
            setting.alphaTitle + transparent + "%"
        )

        setting.messageSizeSpinner.setSelection(
            preference.getInt("BlockMessageSize", 10)
        )

        // 背景圖片
        val backgroundImage = preference.get("BlockBackgroundImage", "")

        if (backgroundImage != "") {
            setting.setBlockBackgroundImage(backgroundImage)
        }

    }

    def save(setting: BlockSetting)
    {
        // 視窗位置、大小
        preference.putInt("BlockX", setting.locationX.getText.toInt)
        preference.putInt("BlockY", setting.locationY.getText.toInt)
        preference.putInt("BlockWidth", setting.width.getText.toInt)
        preference.putInt("BlockHeight", setting.height.getText.toInt)

        // 配色
        preference.putInt("BlockBGRed", setting.bgColor.getRed)
        preference.putInt("BlockBGGreen", setting.bgColor.getGreen)
        preference.putInt("BlockBGBlue", setting.bgColor.getBlue)
        preference.putInt("BlockFontRed", setting.fontColor.getRed)
        preference.putInt("BlockFontGreen", setting.fontColor.getGreen)
        preference.putInt("BlockFontBlue", setting.fontColor.getBlue)
        preference.putInt("BlockBorderRed", setting.borderColor.getRed)
        preference.putInt("BlockBorderGreen", setting.borderColor.getGreen)
        preference.putInt("BlockBorderBlue", setting.borderColor.getBlue)
        preference.putInt(
            "BlockTransparent", 
            setting.transparentScale.getSelection
        )

        // 背景圖片
        setting.blockBackgroundImage match {
            case Some(imageFile) => preference.put("BlockBackgroundImage", imageFile)
            case None => preference.remove("BlockBackgroundImage")
        }

        // 字型
        val fontData = setting.messageFont.getFontData()(0)
        preference.put("BlockFontName", fontData.getName)
        preference.putInt("BlockFontHeight", fontData.getHeight)
        preference.putInt("BlockFontStyle", fontData.getStyle)

        preference.putInt(
            "BlockMessageSize", 
            setting.messageSizeSpinner.getSelection
        )
    }

    def read(setting: BalloonSetting)
    {
        setting.locationX.setText(preference.getInt("BalloonX", 100).toString)
        setting.locationY.setText(preference.getInt("BalloonY", 100).toString)
        setting.width.setText(preference.getInt("BalloonWidth", 200).toString)
        setting.height.setText(preference.getInt("BalloonHeight", 400).toString)

        val bgColor = new Color(
            Display.getDefault, 
            preference.getInt("BalloonBGRed", 0),
            preference.getInt("BalloonBGGreen", 0),
            preference.getInt("BalloonBGBlue", 0)
        )

        val fontColor = new Color(
            Display.getDefault,
            preference.getInt("BalloonFontRed", 255),
            preference.getInt("BalloonFontGreen", 255),
            preference.getInt("BalloonFontBlue", 255)
        )

        val borderColor = new Color(
            Display.getDefault,
            preference.getInt("BalloonBorderRed", 255),
            preference.getInt("BalloonBorderGreen", 255),
            preference.getInt("BalloonBorderBlue", 255)
        )

        setting.bgColor = bgColor
        setting.fontColor = fontColor
        setting.borderColor = borderColor
        setting.bgButton.setText(bgColor)
        setting.fgButton.setText(fontColor)
        setting.borderButton.setText(borderColor)

        val font = new Font(
            Display.getDefault, 
            preference.get("BalloonFontName", MyFont.DefaultFontName),
            preference.getInt("BalloonFontHeight", MyFont.DefaultFontSize),
            preference.getInt("BalloonFontStyle", MyFont.DefaultFontStyle)
        )

        setting.messageFont = font
        setting.fontButton.setText(font)

        val transparent = preference.getInt("BalloonTransparent", 20)
        setting.transparentScale.setSelection(transparent)
        setting.transparentLabel.setText(
            setting.alphaTitle + transparent + "%"
        )

        // 特效設定
        setting.displayTimeSpinner.setSelection(preference.getInt("BalloonDisplayTime", 5))
        setting.fadeTimeSpinner.setSelection(preference.getInt("BalloonFadeTime", 500))
        setting.spacingSpinner.setSelection(preference.getInt("BalloonSpacing", 5))

    }


    def save(setting: BalloonSetting)
    {
        // 視窗位置、大小
        preference.putInt("BalloonX", setting.locationX.getText.toInt)
        preference.putInt("BalloonY", setting.locationY.getText.toInt)
        preference.putInt("BalloonWidth", setting.width.getText.toInt)
        preference.putInt("BalloonHeight", setting.height.getText.toInt)

        // 配色
        preference.putInt("BalloonBGRed", setting.bgColor.getRed)
        preference.putInt("BalloonBGGreen", setting.bgColor.getGreen)
        preference.putInt("BalloonBGBlue", setting.bgColor.getBlue)
        preference.putInt("BalloonFontRed", setting.fontColor.getRed)
        preference.putInt("BalloonFontGreen", setting.fontColor.getGreen)
        preference.putInt("BalloonFontBlue", setting.fontColor.getBlue)
        preference.putInt("BalloonBorderRed", setting.borderColor.getRed)
        preference.putInt("BalloonBorderGreen", setting.borderColor.getGreen)
        preference.putInt("BalloonBorderBlue", setting.borderColor.getBlue)
        preference.putInt(
            "BalloonTransparent", 
            setting.transparentScale.getSelection
        )

        // 字型
        val fontData = setting.messageFont.getFontData()(0)
        preference.put("BalloonFontName", fontData.getName)
        preference.putInt("BalloonFontHeight", fontData.getHeight)
        preference.putInt("BalloonFontStyle", fontData.getStyle)

        // 特效設定
        preference.putInt("BalloonDisplayTime", setting.displayTimeSpinner.getSelection)
        preference.putInt("BalloonFadeTime", setting.fadeTimeSpinner.getSelection)
        preference.putInt("BalloonSpacing", setting.spacingSpinner.getSelection)
    }

    def read(ircSetting: IRCSetting)
    {
        val portText = preference.get("IRCPort", "6667").trim match {
            case ""   => "6667"
            case port => port
        }

        ircSetting.hostText.setText(preference.get("IRCHostname", ""))
        ircSetting.portText.setText(portText)
        ircSetting.nickname.setText(preference.get("IRCNickname", ""))
        ircSetting.channel.setText(preference.get("IRCChannel", ""))
        ircSetting.onJoinButton.setSelection(preference.getBoolean("IRCOnJoin", false))
        ircSetting.onLeaveButton.setSelection(preference.getBoolean("IRCOnLeave", false))
    }

    def save(ircSetting: IRCSetting)
    {
        preference.put("IRCHostname", ircSetting.hostText.getText)
        preference.put("IRCPort", ircSetting.portText.getText)
        preference.put("IRCNickname", ircSetting.nickname.getText)
        preference.put("IRCChannel", ircSetting.channel.getText)
        preference.putBoolean("IRCOnJoin", ircSetting.onJoinButton.getSelection)
        preference.putBoolean("IRCOnLeave", ircSetting.onLeaveButton.getSelection)
    }

    def read(justinSetting: JustinSetting)
    {
        justinSetting.username.setText(preference.get("JTVUsername", ""))
        justinSetting.onJoinButton.setSelection(preference.getBoolean("JTVOnJoin", false))
        justinSetting.onLeaveButton.setSelection(preference.getBoolean("JTVOnLeave", false))
    }

    def save(justinSetting: JustinSetting)
    {
        preference.put("JTVUsername", justinSetting.username.getText)
        preference.putBoolean("JTVOnJoin", justinSetting.onJoinButton.getSelection)
        preference.putBoolean("JTVOnLeave", justinSetting.onLeaveButton.getSelection)
    }

}
