module DayOne (calibrate, calibrateMany) where

import Data.List (elemIndex)
import Data.Maybe (fromMaybe)
import Data.Char (digitToInt)
import Data.Functor (($>))
import Text.Parsec ( parse
                   , string
                   , digit
                   , choice
                   , anyChar
                   , many
                   , eof
                   , ParseError
                   )
import Text.Parsec.String (Parser)
import Text.Parsec.Prim ((<|>))


-- | A CalibrationValue is a two-digit integer.
newtype CalibrationValue = CalibrationValue Int deriving (Show, Eq)

instance Num CalibrationValue where
  (CalibrationValue x) + (CalibrationValue y) = CalibrationValue (x + y)
  (CalibrationValue x) - (CalibrationValue y) = CalibrationValue (x - y)
  (CalibrationValue x) * (CalibrationValue y) = CalibrationValue (x * y)
  abs (CalibrationValue x) = CalibrationValue (abs x)
  signum (CalibrationValue x) = CalibrationValue (signum x)
  fromInteger x = CalibrationValue (fromInteger x)

instance Enum CalibrationValue where
  toEnum x = CalibrationValue x
  fromEnum (CalibrationValue x) = x

makeCalibrationValue :: Int -> CalibrationValue
makeCalibrationValue x
  | x >= 10 && x <= 99 = CalibrationValue x
  | otherwise = CalibrationValue 0

numberWords :: [String]
numberWords = [ "one"
              , "two"
              , "three"
              , "four"
              , "five"
              , "six"
              , "seven"
              , "eight"
              , "nine"
              ]

numberWordToInt :: String -> Int
numberWordToInt word =
  fromMaybe 0 $ elemIndex word $ "zero" : numberWords

-- Create an integer from a list of digits.
digitsToInt :: [Int] -> Maybe Int
digitsToInt [] = Nothing
digitsToInt [x] = Just $ x * 10 + x
digitsToInt (x:xs) = Just $ x * 10 + last xs
digitsToInt' :: [Int] -> Int
digitsToInt' = fromMaybe 0 . digitsToInt

-- Parser for single digit integers as english words.
numberWord :: Parser String
numberWord = choice $ map string numberWords

-- Parser for integers as english words.
numberWordParser :: Parser Int
numberWordParser = numberWordToInt <$> numberWord

-- Parser for single digit integers.
digitParser :: Parser Int
digitParser = digitToInt <$> digit

-- Parser for integers as english words or single digit integers.
numberWordOrDigit :: Parser Int
numberWordOrDigit = digitParser <|> numberWordParser

manyNumberWordOrDigit :: Parser [Int]
manyNumberWordOrDigit = many (numberWordOrDigit <|> anyChar $> 0) <* eof

-- Parse the digits in the string and return them as a list of integers.
parseCalibrationValue :: String -> Either ParseError [Int]
parseCalibrationValue = parse manyNumberWordOrDigit ""

-- | Takes the first and last integer in a string and returns them as a CalibrationValue.
--   The string can contain any number of characters, and only the first and last integers are used.
--   An integer can be a single digit or the english word for a single digit.
--    Examples:
--     "1abc2" -> 12
--     "pqr3stu8vwx" -> 38
--     "one2three" -> 13
calibrate :: String -> CalibrationValue
calibrate [] = CalibrationValue 0
calibrate s = case parseCalibrationValue s of
  Left _ -> CalibrationValue 0
  Right parsed -> makeCalibrationValue $ digitsToInt' parsed
  
-- | Takes a list of strings and returns a list of CalibrationValues
calibrateMany :: [String] -> [CalibrationValue]
calibrateMany = map calibrate

