module DayTwo ( Color (Red, Green, Blue)
              , parseGame
              , indicesOfPassingGames
              ) where

import Text.Parsec
import Text.Parsec.String (Parser)
import Text.Parsec.Char
import Text.Parsec.Combinator
import Data.Char (toLower)

-- | A Color is either Red, Green, or Blue
data Color = Red | Green | Blue deriving (Show, Eq)

-- Parses a single color
colorParser :: Parser Color
colorParser = do
  color <- many1 letter
  case map toLower color of
    "red" -> return Red
    "green" -> return Green
    "blue" -> return Blue
    _ -> parserFail "Invalid color"


-- Parses a number and color into a tuple
colorNumberPair :: Parser (Color, Int)
colorNumberPair = do
  n <- many1 digit
  space
  c <- colorParser
  return (c, read n)

-- Parses a list of color number pairs
pairListParser :: Parser [(Color, Int)]
pairListParser = colorNumberPair `sepBy` try (string ", ")

-- Parses the game header
gameHeader :: Parser ()
gameHeader = do
  try $ string "Game "
  many1 digit
  string ":"
  spaces
  return ()

-- Parses the entire input
gameParser :: Parser [[(Color, Int)]]
gameParser = do
  gameHeader
  pairListParser `sepBy` try (string "; ")


-- | Parse a game input string int a list of color number pairs
parseGame :: String -> Either ParseError [[(Color, Int)]]
parseGame = parse gameParser ""

-- | Check if a game has less color counts than the given rules
checkGame :: [(Color, Int)] -> [(Color, Int)] -> Bool
checkGame rules = all checkColorCount
  where
    checkColorCount (color, count) = case lookup color rules of
        Just ruleCount -> count <= ruleCount
        Nothing -> False -- Or True, depending on how you want to handle colors not in the rules

-- | Check if all games have less color counts than the given rules
checkGames :: [(Color, Int)] -> [[(Color, Int)]] -> Bool
checkGames rules = all (checkGame rules)

-- | Get indices of games that pass the check
indicesOfPassingGames :: [(Color, Int)] -> [[(Color, Int)]] -> [Int]
-- indicesOfPassingGames rules games = map fst $ filter (checkGames rules . snd) $ zip [0..] games
indicesOfPassingGames rules = map fst $ filter (checkGames rules) . zip [0..]
