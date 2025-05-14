import subprocess
import sys

def delete_user_folder(name):
    delete_cmd = f"rm -rf /home/{name}"
    subprocess.run(delete_cmd, shell=True)

def encode_video(mode):
    subprocess.run(["ffmpeg","-i", "-preset", mode], shell=True)

default_admin_action = "whoami"
subprocess.run(default_admin_action, shell=True)

def run_admin_command():
    subprocess.run(default_admin_action, shell=True)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python script.py <username_or_file>")
        sys.exit(1)

    target = sys.argv[1]

    delete_user_folder(target)
    create_user_file_safe(target)
    encode_video(target)
    run_admin_command(f"ls -la /home/{target}")
