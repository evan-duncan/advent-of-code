module DayOneSpec (main, spec) where

import Lib (readPuzzleInput)
import DayOne (calibrate, calibrateMany)
import Test.Hspec

main :: IO ()
main = hspec spec

spec :: Spec
spec = do
  describe "Day One" $ do
    describe "calibrate" $ do
      it "should return 12 for \"1abc2\"" $ do
        calibrate "1abc2" `shouldBe` 12

      it "should return 38 for \"pqr3stu8vwx\"" $ do
        calibrate "pqr3stu8vwx" `shouldBe` 38

      it "should return 15 for \"a1b2c3d4e5f\"" $ do
        calibrate "a1b2c3d4e5f" `shouldBe` 15

      it "should return 77 for \"treb7uchet\"" $ do
        calibrate "treb7uchet" `shouldBe` 77

      it "should return 29 for \"two1nine\"" $ do
        calibrate "two1nine" `shouldBe` 29

      it "should return 83 for \"eightwothree\"" $ do
        calibrate "eightwothree" `shouldBe` 83

      it "should return 13 for \"abcone2threexyz\"" $ do
        calibrate "abcone2threexyz" `shouldBe` 13

      it "should return 24 for \"xtwone3four\"" $ do
        calibrate "xtwone3four" `shouldBe` 24

      it "should return 42 for \"4nineeightseven2\"" $ do
        calibrate "4nineeightseven2" `shouldBe` 42

      it "should return 14 for \"zoneight234\"" $ do
        calibrate "zoneight234" `shouldBe` 14

      it "should return 76 for \"7pqrstsixteen\"" $ do
        calibrate "7pqrstsixteen" `shouldBe` 76

    describe "calibrateMany" $ do
      it "should return [12, 38] for [\"1abc2\", \"pqr3stu8vwx\"]" $ do
        calibrateMany ["1abc2", "pqr3stu8vwx"] `shouldBe` [12, 38]

    it "solves the puzzle" $ do
      input <- readPuzzleInput 2023 1 "test/.cache/day_one.txt"
      sum (calibrateMany input) `shouldBe` 57346 -- Part One
