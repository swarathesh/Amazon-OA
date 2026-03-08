# Amazon-OA

Repository for practicing Amazon Online Assessment (OA) problems and interview-style questions.

## About

This repo collects solutions, notes, and small scripts used to practice for Amazon online assessments and technical interviews. The goal is to keep short, well-documented solutions organized by topic so you can quickly review patterns and run examples locally.

## Repository structure

- `arrays/`, `strings/`, `graphs/`, `dp/`, ... — folders holding problem solutions grouped by topic.
- `scripts/` — small utilities or test harnesses used across problems.
- `README.md` — this file.

Each solution file should include a short comment with the problem name, source (link if available), time/space complexity, and a quick usage example.

## How to use

1. Clone the repository (if you haven't already):

	git clone https://github.com/swarathesh/Amazon-OA.git

2. Navigate to the folder for the topic you're interested in and run the solution file. Examples below assume Python solutions, but many solutions may be in other languages (see file extensions):

	```zsh
	# run a Python solution
	python3 arrays/two_sum.py
	```

3. If a solution includes a simple test harness or sample input, it will print results when run. Otherwise, import the function in an interactive session or write a tiny wrapper to try custom inputs.

## Conventions

- File naming: `problem_name.[py|js|cpp]` (lowercase, underscores for spaces).
- Include problem statement or link in the top comments of the file.
- Include complexity analysis and at least one test case in a `if __name__ == "__main__":` block for runnable languages.

## Contributing

Contributions are welcome. When adding a new solution:

1. Place it in the appropriate topic folder. Create a new folder if necessary.
2. Add a short comment with the problem title and source/link.
3. Provide a sample input/test and complexity notes.

If you'd like, open a PR or add an issue describing the problem you'd like to add.

## License

This repo is intended for practice and sharing educational solutions. Add a LICENSE file if you want to set formal licensing terms.

## Contact

Owner: `swarathesh` (GitHub)

---

Happy practicing — good luck on your Amazon OA! 🚀
